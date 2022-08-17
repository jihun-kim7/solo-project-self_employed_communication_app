package soloproject.api.v1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {

    public Member(Long memberId, String name, String password, String sex, String companyName, CompanyLocation companyLocation, CompanyType companyType) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companyType = companyType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "location_Id")
    private CompanyLocation companyLocation;

    @ManyToOne
    @JoinColumn(name = "type_Id")
    private CompanyType companyType;
}
