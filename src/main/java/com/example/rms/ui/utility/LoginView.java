package com.example.rms.ui.utility;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("login")
@PageTitle("Login | RMS")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login = new LoginForm();
    RouterLink route = new RouterLink("Registration", RegistrationView.class);

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(
                new H1("Rabbit Management System"),
                login,
                route
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setEnabled(true);
        }
    }
}
