package api.services;

import api.base.BaseApi;
import api.endpoints.UserEndpoints;
import api.models.user.User;
import io.restassured.response.Response;

import java.util.List;

public class UserService extends BaseApi {

    public UserService(String baseUrl) {
        super(baseUrl);
    }

    public Response createUsers(List<User> users) {
        return request
                .body(users)
                .post(UserEndpoints.CREATE_USERS);
    }

    public Response getUserByUsername(String username) {
        return request
                .pathParam("username", username)
                .get(UserEndpoints.GET_USER_BY_USERNAME);
    }

    public Response updateUser(String username, User user) {
        return request
                .pathParam("username", username)
                .body(user)
                .put(UserEndpoints.UPDATE_USER);
    }

    public Response deleteUser(String username) {
        return request
                .pathParam("username", username)
                .delete(UserEndpoints.DELETE_USER);
    }
}
