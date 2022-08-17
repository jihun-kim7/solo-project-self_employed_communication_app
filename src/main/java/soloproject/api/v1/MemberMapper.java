package soloproject.api.v1;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    default List<MemberResponseDto> membersToMemberResponses(List<Member> members) {
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
        for (Member member : members) {
            memberResponseDtoList.add(new MemberResponseDto(member.getMemberId(), member.getName(), member.getSex(),
                    member.getCompanyName(), member.getCompanyLocation(), member.getCompanyType()));
        }
        return memberResponseDtoList;
    }
}
