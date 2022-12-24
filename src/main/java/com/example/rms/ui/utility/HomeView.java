package com.example.rms.ui.utility;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "", layout = RootLayout.class)
@PageTitle("Home | RMS")
@PermitAll
public class HomeView extends VerticalLayout {
    private final Button manageDoes = new Button("Manage Does");
    private final Button manageBucks = new Button("Manage Bucks");
    private final Button manageSales = new Button("Manage SalesView");
    private final Button manageWeaners = new Button("Manage Weaners");
    private final Button settings = new Button("System Settings");
    private final Button reports = new Button("Excel Report");
    private final Button taskTracker = new Button("Update Task Tracker");

    public HomeView() {
        addClassName("home-view");
        setSizeFull();

        add(
                createButtonLayout(),
                configureMessage()
        );
    }
    public Component createButtonLayout() {
        Label header = new Label("System Overview");

        manageBucks.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        manageDoes.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        manageSales.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        manageWeaners.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        settings.addThemeVariants(ButtonVariant.LUMO_SMALL);
        reports.addThemeVariants(ButtonVariant.LUMO_SMALL);

        settings.getStyle().set("background-color", "#497691").set("color", "#fff");
        reports.getStyle().set("background-color", "#497691").set("color", "#fff");

        manageDoes.addClickListener(e ->
                manageDoes.getUI().ifPresent(ui ->
                        ui.navigate("does")));
        manageBucks.addClickListener(e ->
                manageBucks.getUI().ifPresent(ui ->
                        ui.navigate("bucks")));
        manageWeaners.addClickListener(e ->
                manageWeaners.getUI().ifPresent(ui ->
                        ui.navigate("weaners")));
        manageSales.addClickListener(e ->
                manageSales.getUI().ifPresent(ui ->
                        ui.navigate("sales")));
        settings.addClickListener(e ->
                settings.getUI().ifPresent(ui ->
                        ui.navigate("system settings")));

        HorizontalLayout layout = new HorizontalLayout(
                manageDoes, manageBucks,manageWeaners,manageSales);
        HorizontalLayout smallButtons = new HorizontalLayout(
                 reports);
        return new VerticalLayout(
                header,
                layout,
                smallButtons
        );
    }
    private Component configureMessage() {
        H3 message = new H3("Click the button "+"below to update the task tracker");
        message.getStyle()
                .set("font-size","var(--lumo-font-size-l")
                .set("margin", "4");

        taskTracker.addThemeVariants(ButtonVariant.LUMO_SMALL);
        taskTracker.getStyle().set("background-color", "#497691").set("color", "#fff");

        taskTracker.addClickListener(e ->taskTracker
                .getUI().ifPresent(ui ->ui.navigate("Doe Tracker")));

        VerticalLayout middLayout = new VerticalLayout(message, taskTracker);
        middLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        return middLayout;
    }
}
