package com.example.rms.ui.utility;

import com.example.rms.security.SecurityService;
import com.example.rms.ui.lists.bucks.BuckList;
import com.example.rms.ui.lists.does.DoeView;
import com.example.rms.ui.lists.weaners.WeanerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class RootLayout extends AppLayout {

    private final SecurityService securityService;

    public RootLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Rabbit Management");
        logo.addClassName("logo");
        logo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "4");

        Button logOut = new Button("Log out", e -> securityService.logout());
        logOut.addClassName("logOut");
        Button home = new Button(logo);

        home.addClickListener(e ->home.getUI().ifPresent(ui ->
                        ui.navigate("")));
        home.getStyle().set("background-color", "#497691");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), home, logOut);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.expand(logo);
        header.setWidthFull();
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink doeView = new RouterLink("Does", DoeView.class);
        RouterLink buckView = new RouterLink("Bucks", BuckList.class);
        RouterLink tracker = new RouterLink("Task Tracker", DoeTracker.class);
        RouterLink weanerView = new RouterLink("Weaner", WeanerView.class);
        RouterLink registration = new RouterLink("Registration", RegistrationView.class);
        doeView.setHighlightCondition(HighlightConditions.sameLocation());
        VerticalLayout toggleLayout = new VerticalLayout(
                buckView, doeView, tracker, weanerView,registration);

        addToDrawer(toggleLayout);
    }
}
