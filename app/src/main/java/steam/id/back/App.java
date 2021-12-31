package steam.id.back;

import lombok.extern.slf4j.Slf4j;

import static spark.Spark.*;

@Slf4j
public class App {
    public static void main(String[] args) {
        port(8002);
        get("/profile/:user", (req, res) -> UserUtil.getUser(req.params(":user")));
    }
}
