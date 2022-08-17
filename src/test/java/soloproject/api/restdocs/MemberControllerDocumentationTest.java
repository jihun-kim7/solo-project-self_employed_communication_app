package soloproject.api.restdocs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import soloproject.api.v1.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    private static List<Member> members;
    private static List<MemberResponseDto> members1;
    private static List<MemberResponseDto> members2;
    private static List<MemberResponseDto> members3;
    private static List<MemberResponseDto> members4;

    @BeforeAll
    public static void beforeAll() {
        CompanyLocation companyLocation1 = new CompanyLocation(1L,"서울");
        CompanyLocation companyLocation2 = new CompanyLocation(2L,"부천");

        CompanyType companyType1 = new CompanyType(1L, "요식업");
        CompanyType companyType2 = new CompanyType(2L, "금융업");
        CompanyType companyType3 = new CompanyType(3L, "교육업");

        MemberResponseDto member1 = new MemberResponseDto(1L,"김코딩",  "M", "프로젝트스테이츠", companyLocation1, companyType3);
        MemberResponseDto member2 = new MemberResponseDto(2L,"김코딩",  "M", "쿄쿄치킨집", companyLocation1, companyType1);
        MemberResponseDto member3 = new MemberResponseDto(3L,"이코딩",  "M", "개성만두집", companyLocation1, companyType1);
        MemberResponseDto member4 = new MemberResponseDto(4L,"박코딩",  "W", "페리카나", companyLocation2, companyType1);
        MemberResponseDto member5 = new MemberResponseDto(5L,"지코딩",  "W", "호호스테이츠", companyLocation2, companyType3);
        MemberResponseDto member6 = new MemberResponseDto(6L,"성코딩",  "M", "킥킥금융", companyLocation2, companyType2);

        members = List.of(new Member(1L,"김코딩", "12345", "M", "프로젝트스테이츠", companyLocation1, companyType3));
        members1 = List.of(member1, member2, member3, member4, member5, member6);
        members2 = List.of(member2, member3, member4);
        members3 = List.of(member4, member5, member6);
        members4 = List.of(member2, member3);
    }

    @Test
    public void getMembersTest() throws Exception {
        // TODO 여기에 MemberController의 getMembers() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        // given
        given(memberService.getMemebers()).willReturn(members);
        given(mapper.membersToMemberResponses(Mockito.any())).willReturn(members1);

        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/members"));

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(6))
                .andDo(document("get-members",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("사업장이름"),
                                        subsectionWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("지역"),
                                        subsectionWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("업종")
                                )
                        )
                ))
                .andReturn();

    }

    @Test
    public void getMemebersByTypeIdTest() throws Exception {
        // TODO 여기에 MemberController의 getMembers() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        // given
        given(memberService.getMemebersByTypeId(1L)).willReturn(members);
        given(mapper.membersToMemberResponses(Mockito.any())).willReturn(members2);
        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/members")
                .param("typeId","1"));

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andDo(document("get-membersByTypeId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("typeId").description("업종")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("사업장이름"),
                                        subsectionWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("지역"),
                                        subsectionWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("업종")
                                )
                        )
                ))
                .andReturn();

    }


    @Test
    public void getMemebersByLocationIdTest() throws Exception {
        // TODO 여기에 MemberController의 getMembers() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        // given
        given(memberService.getMemebersByLocationId(2L)).willReturn(members);
        given(mapper.membersToMemberResponses(Mockito.any())).willReturn(members3);
        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/members")
                .param("locationId","2"));

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andDo(document("get-membersByLocationId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("locationId").description("지역")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("사업장이름"),
                                        subsectionWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("지역"),
                                        subsectionWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("업종")
                                )
                        )
                ))
                .andReturn();

    }

    @Test
    public void getMemebersByTypeIdAndLocationIdTest() throws Exception {
        // TODO 여기에 MemberController의 getMembers() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        // given
        given(memberService.getMembersByTypeIdAndLocationId(1L,1L)).willReturn(members);
        given(mapper.membersToMemberResponses(Mockito.any())).willReturn(members4);

        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/members")
                .param("typeId","1").param("locationId","1"));

        // then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andDo(document("get-membersByTypeIdAndLocationId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("typeId").description("업종"),
                                parameterWithName("locationId").description("지역")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("사업장이름"),
                                        subsectionWithPath("data[].companyLocation").type(JsonFieldType.OBJECT).description("지역"),
                                        subsectionWithPath("data[].companyType").type(JsonFieldType.OBJECT).description("업종")
                                )
                        )
                ))
                .andReturn();

    }


}
