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

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //    @GetMapping("/type/{TypeId}")
//    public ResponseEntity getMemberByType(
//            @PathVariable("TypeId") String TypeId) {
//        Member member = memberService.findMemberByType(TypeId);
//        return new ResponseEntity<>(member, HttpStatus.OK);
//    }
//
//    @GetMapping("location/{locationId}")
//    public ResponseEntity getMemberByLocation(
//            @PathVariable("locationId") String locationId
//    ) {
//        Member member = memberService.findMemberByLocation(locationId);
//        return new ResponseEntity<>(member, HttpStatus.OK);
//    }
//
//    @GetMapping("/type/{TypeId}/location/{locationId}")
//    public ResponseEntity getMemberByTypeAndLocation(
//            @PathVariable("TypeId") String typeId,
//            @PathVariable("locationId") String locationId
//    ) {
//        Member member = memberService.findMemberByTypeAndLocation(typeId,locationId);
//        return new ResponseEntity<>(member, HttpStatus.OK);
//    }

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

        return new ResponseEntity<>(new MultiResponseDto<>(members), HttpStatus.OK);
    }
}
