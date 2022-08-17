package soloproject.api.v1;

import lombok.Getter;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> data;

    public MultiResponseDto(List<T> data) {
        this.data = data;
    }
}
