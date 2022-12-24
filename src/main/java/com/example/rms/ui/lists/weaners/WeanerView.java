package com.example.rms.ui.lists.weaners;

import com.example.rms.entities.weaner.Weaner;
import com.example.rms.entities.weaner.WeanerService;
import com.example.rms.ui.utility.RootLayout;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.annotation.security.PermitAll;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.stream.Stream;

@Route(value = "weaners", layout = RootLayout.class)
@PermitAll
public class WeanerView extends VerticalLayout {
    private final WeanerService weanerService;

    Grid<Weaner> grid = new Grid<>(Weaner.class, false);
    TextField filterText = new TextField();
    WeanerForm form;

    public WeanerView(WeanerService weanerService) {
        this.weanerService = weanerService;

        addClassName("buck-list");
        setSizeFull();

        configureGrid();
        configureForm();

        updateList();
        closeEditor();
        add(
                new Label("Select weaner group to change"),
                getToolBar(),
                getContent()
        );
    }

    private void closeEditor() {
        form.setWeaner(null);
        form.setVisible(false);
        removeClassName("editing2");
    }

    private void updateList() {
        grid.setItems(weanerService.findAllWeaner(filterText.getValue()));
        weanerService.countWeaner();
    }

    private Component getContent() {
//        HorizontalLayout content = new HorizontalLayout(grid, form);
        VerticalLayout content = new VerticalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private Component getToolBar() {
        Button addWeaner = new Button("Add Weaner");
        addWeaner.addClickListener(e -> addWeaner());
        filterText.setPlaceholder("Filter by cage ref");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        var streamResource = new StreamResource("weaners.csv",
                () -> {
                    Stream<Weaner> weaner = grid.getGenericDataView().getItems();
                    StringWriter output = new StringWriter();
                    var beanToCv = new StatefulBeanToCsvBuilder<Weaner>(output).build();
                    try {
                        beanToCv.write(weaner);
                        var contents = output.toString();
                        return new ByteArrayInputStream(contents.getBytes());
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                        return null;
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                        return null;
                    }

                });

        var download = new Anchor(streamResource, "Excel Report");
        Button down = new Button(download);


        HorizontalLayout toolBar = new HorizontalLayout(
                filterText, addWeaner, down);
        toolBar.addClassName("toolbar2");

        return toolBar;
    }

    private void addWeaner() {
        grid.asSingleSelect().clear();
        editWeaner(new Weaner());
    }

    private void configureForm() {
        form = new WeanerForm();
        form.setWidth("25em");

        form.addListener(WeanerForm.SaveEvent.class
                , this::saveWeaner);
        form.addListener(WeanerForm.DeleteEvent.class
                , this::deleteWeaner);
        form.addListener(WeanerForm.CloseEvent.class
                , e -> closeEditor());

        grid.asSingleSelect().addValueChangeListener(
                e -> editWeaner(e.getValue())
        );
    }

    private void deleteWeaner(WeanerForm.DeleteEvent event) {
        weanerService.deleteWeaner(event.getWeaner());
        updateList();
        closeEditor();
    }

    private void saveWeaner(WeanerForm.SaveEvent event) {
        weanerService.saveWeaner(event.getWeaner());
        updateList();
        closeEditor();
    }

    private void editWeaner(Weaner weaner) {
        if (weaner == null) {
            closeEditor();
        } else {
            form.setWeaner(weaner);
            form.setVisible(true);
            addClassName("editing2");
        }
    }

    private void configureGrid() {
        addClassName("weaner-grid");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addColumn(Weaner::getCageRef).setHeader("Cage Ref")
                .setFooter(String.format("%s Total Weaners", weanerService.countWeaner()));
        grid.addColumn(Weaner::getdOB).setHeader("D.O.B");
        grid.addColumn(Weaner::getInitialCount).setHeader("Initial");
        grid.addColumn(Weaner::getCurrentCount).setHeader("Current");
        grid.addColumn(Weaner::getParentDoe).setHeader("Parent Doe");
        grid.addColumn(Weaner::getSire).setHeader("Sire");

        updateList();
    }
}
