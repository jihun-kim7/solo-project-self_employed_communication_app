package soloproject.api.v1;

import lombok.Getter;

import java.util.List;

@Getter
public class SingleResponseDto<T> {
    private List<T> data;

    public SingleResponseDto(List<T> data) {
        this.data = data;
    }
}
