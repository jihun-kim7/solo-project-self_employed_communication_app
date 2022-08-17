package soloproject.api.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface MemberRepository extends JpaRepository<Member, Long> {
    default List<Member> findByTypeId(Long typeId) {
        return this.findAll()
                .stream()
                .filter(member -> member.getCompanyType().getTypeId() == typeId)
                .collect(Collectors.toList());
    }


    default List<Member> findByLocationId(Long locationId){
        return this.findAll()
                .stream()
                .filter(member -> member.getCompanyLocation().getLocationId() == locationId)
                .collect(Collectors.toList());
    }

    default List<Member> findByTypeIdAndLocationId(Long typeId, Long locationId) {
        return this.findAll()
                .stream()
                .filter(member -> member.getCompanyType().getTypeId() == typeId &&
                        member.getCompanyLocation().getLocationId() == locationId)
                .collect(Collectors.toList());
    }


}
