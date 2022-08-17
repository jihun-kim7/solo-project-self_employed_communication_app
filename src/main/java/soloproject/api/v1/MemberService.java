package soloproject.api.v1;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMemebers(){
        return memberRepository.findAll();
    }

    public List<Member> getMemebersByTypeId(Long typeId){
        return memberRepository.findByTypeId(typeId);
    }

    public List<Member> getMemebersByLocationId(Long locationId){
        return memberRepository.findByLocationId(locationId);
    }

    public List<Member> getMembersByTypeIdAndLocationId(Long typeId, Long locationId) {
        return memberRepository.findByTypeIdAndLocationId(typeId, locationId);
    }


}
