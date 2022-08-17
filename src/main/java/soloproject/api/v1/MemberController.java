package soloproject.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Validated
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @RequestMapping
    public ResponseEntity getMembers(
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long locationId
    ) {
        List<Member> members;

        if (typeId != null && locationId != null) {
            members = memberService.getMembersByTypeIdAndLocationId(typeId, locationId);
        } else if (typeId != null) {
            members = memberService.getMemebersByTypeId(typeId);
        } else if (locationId != null) {
            members = memberService.getMemebersByLocationId(locationId);
        } else {
            members = memberService.getMemebers();
        }

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.membersToMemberResponses(members)), HttpStatus.OK);
    }
}
