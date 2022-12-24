package com.example.rms.ui.utility;

import com.example.rms.entities.does.Doe;
import com.example.rms.entities.does.DoeService;
import com.example.rms.entities.weaner.Weaner;
import com.example.rms.entities.weaner.WeanerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.List;

@Route(value = "Doe Tracker", layout = RootLayout.class)
@PageTitle("Task Doe | RMS")
@PermitAll
public class DoeTracker extends VerticalLayout {
    H2 topLayout = new H2("Task Tracker");
    Grid<Doe> grid1 = new Grid<>(Doe.class, false);
    Grid<Doe> grid2 = new Grid<>(Doe.class, false);
    Grid<Weaner> grid3 = new Grid<>(Weaner.class, false);
    DoeService doeService;
    WeanerService weanerService;

    public DoeTracker(DoeService doeService, WeanerService weanerService) {
        this.weanerService = weanerService;
        this.doeService = doeService;
        setSizeFull();

        add(
                topLayout(),
                configureButton(),
                configureAll()
        );
        setSpacing(false);
        updateList();
    }

    private Component configureGrid3() {
        List<Weaner> weaners = weanerService.findAll();

        grid3.addClassName("task-grid3");
        grid3.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        Grid.Column<Weaner> cageRef = grid3
                .addColumn(Weaner::getParentDoe).setHeader("");
        Grid.Column<Weaner> localDate = grid3
                .addColumn(weaner -> weaner.getdOB().plusDays(23)).setHeader("Due");

        HeaderRow headerRow = grid3.prependHeaderRow();
        headerRow.join(cageRef, localDate).setText(createWeanerHeader(weaners));
        return grid3;
    }

    private Component mateStatusGrid() {
        List<Doe> does = doeService.getAllDoes();
        grid2.addClassName("task-grid2");
        grid2.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        Grid.Column<Doe> cageRef = grid2
                .addColumn(Doe::getCageRef).setHeader("");
        Grid.Column<Doe> localDate = grid2
                .addColumn(doe -> doe.isMated() ? LocalDate.now().plusDays(10) : "-")
                .setHeader("Due");

        HeaderRow headerRow = grid2.prependHeaderRow();
        headerRow.join(cageRef, localDate).setText(createMatingHeader(does));

        grid2.setItems(does);
        return grid2;
    }

    private Component configureGridPlaceBox() {
        List<Doe> does = doeService.getAllDoes();

        grid1.addClassName("task-grid1");
        grid1.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        Grid.Column<Doe> cageRef = grid1
                .addColumn(Doe::getCageRef).setHeader("");
        Grid.Column<Doe> localDate = grid1
                .addColumn(doe -> doe.isPregnant() ? LocalDate.now().plusDays(18) : "-")
                .setHeader("Due");

        HeaderRow headerRow = grid1.prependHeaderRow();
        headerRow.join(cageRef, localDate).setText(createPlaceBoxHeader(does));

        return grid1;
    }

    private Button configureButton() {
        Button update = new Button("Update Now");
        update.addClassName("update-tracker");
        update.addThemeVariants(ButtonVariant.LUMO_SMALL);
        return update;
    }

    private void updateList() {
        grid1.setItems(doeService.findAll_checkPregnancy(true));
        grid2.setItems(doeService.findAll_placeBox(true));
        grid3.setItems(weanerService.findAll());
    }

    public Component configureAll() {
        HorizontalLayout grids = new HorizontalLayout(
                mateStatusGrid(),
                configureGridPlaceBox(),
                configureGrid3()
        );
        grids.setSizeFull();

        return grids;
    }

    private Component topLayout() {
        topLayout.addClassName("topLayout-tracker");
        return topLayout;
    }

    public Object getDays() {
        LocalDate now = LocalDate.now();
        now.plusDays(10);
        return now;
    }

    private static String createPlaceBoxHeader(List<Doe> does) {
        long placeBox = does.stream()
                .filter(doe -> doe.isPregnant())
                .count();
        String.format("%s", placeBox);
        return "Place Box" + "(" + placeBox + ")";
    }
    private static String createMatingHeader(List<Doe> does) {
        long checkPregnancy = does.stream()
                .filter(doe -> doe.isMated())
                .count();
        String.format("%s", checkPregnancy);
        return "Check Pregnancy" + "(" + checkPregnancy + ")";
    }
    private static String createWeanerHeader(List<Weaner> weaners) {
        long weanersCount = weaners.stream().count();
        String.format("%s", weanersCount);

        return "Wean Kits" + "(" + weanersCount + ")";
    }
}
