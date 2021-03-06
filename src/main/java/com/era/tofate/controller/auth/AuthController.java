package com.era.tofate.controller.auth;

import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.auth.AuthRequest;
import com.era.tofate.payload.auth.UserToken;

import com.era.tofate.service.user.UserService;
import com.era.tofate.service.userrole.UserRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final UserRoleService userRoleService;
    private final UserService service;

    /**
     * User Authorisation
     *
     * @param  request - User
     * @return authorised token
     */
    @ApiOperation(value = "Authentication using login and password",
            notes = "Also returns a token to use other services")
    @PostMapping(value = "/api/user/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserToken> auth(@RequestBody AuthRequest request) {
        try {
            UserToken response = service.auth(request.getLogin(), request.getPassword());
            if (response != null){
                response.setRoles(userRoleService.findAllByUser_Login(request.getLogin()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new AuthenticationException(NO_ACCESS);
            }
        } catch (Exception ex) {
            throw new BadRequestException(NO_ACCESS);
        }

    }

}
