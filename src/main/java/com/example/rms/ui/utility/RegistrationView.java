package com.example.rms.ui.utility;

import com.example.rms.entities.user.User;
import com.example.rms.entities.user.UserRepo;
import com.example.rms.entities.user.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Route("registration")
@PageTitle("Registration | RMS")
@AnonymousAllowed
public class RegistrationView extends Composite {
    private final UserService userService;

    H2 heading = new H2("Register");
    TextField username = new TextField("Username");
    EmailField emailField = new EmailField("Email");
    PasswordField passwordField1 = new PasswordField("Password");
    PasswordField passwordField2 = new PasswordField("Confirm Password");
    Binder<User> binder = new BeanValidationBinder<>(User.class);
    UserRepo repo;

    public RegistrationView(UserService userService, UserRepo repo) {

        this.userService = userService;
        this.repo = repo;
    }

    @Override
    protected Component initContent() {
        Button button = new Button("Register", event -> register(
                username.getValue(),
                emailField.getValue(),
                passwordField1.getValue(),
                passwordField2.getValue()
        ));
        VerticalLayout layout = new VerticalLayout(
                heading,
                username,
                emailField,
                passwordField1,
                passwordField2,
                button
        );
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setSpacing(false);
        return layout;
    }

    private void register(String username, String email, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwords don't match");

        } else if (password1.isEmpty()) {
            Notification.show("Enter a password");
        }else {
            Notification.show("Registration successful");
            userService.register(username, email, password1);

            UI.getCurrent().getPage().setLocation("login");
        }
    }
}
