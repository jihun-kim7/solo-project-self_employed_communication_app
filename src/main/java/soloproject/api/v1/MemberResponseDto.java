package soloproject.api.v1;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
public class MemberResponseDto {
    private Long memberId;

    private String name;

    private String sex;

    private String companyName;

    private CompanyLocation companyLocation;

    private CompanyType companyType;
}
