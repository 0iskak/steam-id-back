package steam.id.back;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    String image;
    String name;
    Long created;
    String status;
    String visibility;
    String steamId;
    String steamId3;
    Long steamId32;
    Long steamId64;
    String nick;
    String profileUrl;
    String permanentUrl;
    Integer gamesBan;
    Boolean communityBan;
    Boolean vacBan;
    Boolean tradeBan;

    @Override
    public String toString() {
        return UserUtil.json(this);
    }
}