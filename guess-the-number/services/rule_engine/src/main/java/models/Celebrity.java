package models;

import com.mindsmiths.sdk.core.api.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Celebrity extends Event {
    String name;
    int followerCount;
    String description;
    String imageUrl;
}
