package com.example.rms.ui.lists.bucks;

import com.example.rms.entities.bucks.Buck;
import com.example.rms.entities.bucks.BuckService;
import com.example.rms.entities.does.Doe;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.annotation.security.PermitAll;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Stream;

@Route(value = "bucks", layout = RootLayout.class)
@PageTitle("Bucks | RMS")
@PermitAll
public class BuckList extends VerticalLayout {
    private final BuckService buckService;

    Grid<Buck> grid = new Grid<>(Buck.class, false);
    TextField filterText = new TextField();
    BuckForm form;

    public BuckList(BuckService buckService) {
        this.buckService = buckService;

        addClassName("buck-list");
        setSizeFull();

        configureGrid();
        configureForm();

        updateList();
        closeEditor();
        add(
                new Label("Select buck to change"),
                getToolBar(),
                getContent()
        );
    }

    private void closeEditor() {
        form.setBuck(null);
        form.setVisible(false);
        removeClassName("editing1");
    }

    private void updateList() {
        grid.setItems(buckService.findAllBucks(filterText.getValue()));
        buckService.countBucks();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }
    private Component getToolBar() {
        Button addBuck = new Button("Add Buck");
        addBuck.addClickListener(e -> addBucks());
        filterText.setPlaceholder("Filter by cage ref");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        var streamResource = new StreamResource("bucks.csv",
                () ->{
                    Stream<Buck> bucks = grid.getGenericDataView().getItems();
                    StringWriter output = new StringWriter();
                    var beanToCv = new StatefulBeanToCsvBuilder<Buck>(output).build();
                    try {
                        beanToCv.write(bucks);
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
                filterText, addBuck, down);
        toolBar.addClassName("toolbar1");

        return toolBar;
    }

    private void addBucks() {
        grid.asSingleSelect().clear();
        editBuck(new Buck());
    }

    private void configureForm() {
        form = new BuckForm();
        form.setWidth("25em");

        form.addListener(BuckForm.SaveEvent.class
                , this::saveBuck);
        form.addListener(BuckForm.DeleteEvent.class
                , this::deleteBuck);
        form.addListener(BuckForm.CloseEvent.class
                , e -> closeEditor());

        grid.asSingleSelect().addValueChangeListener(
                e -> editBuck(e.getValue())
        );
    }

    private void deleteBuck(BuckForm.DeleteEvent event) {
        buckService.deleteBuck(event.getBuck());
        updateList();
        closeEditor();
    }
    private void saveBuck(BuckForm.SaveEvent event) {
        buckService.saveBuck(event.getBuck());
        updateList();
        closeEditor();
    }
    private void editBuck(Buck buck) {
        if (buck == null) {
            closeEditor();
        } else {
            form.setBuck(buck);
            form.setVisible(true);
            addClassName("editing1");
        }
    }

    private void configureGrid() {
        List<Buck> bucks = buckService.getAllBucks();
        addClassName("buck-grid");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addColumn(Buck::getCageRef).setHeader("Cage Ref")
                .setFooter(String.format("%s Total Does", buckService.countBucks()));
        grid.addColumn(Buck::getBreed)
                .setHeader("Breed")
                .setFooter(breedFooter(bucks));
        grid.addColumn(Buck::getWeight).setHeader("Weight");

        updateList();
    }
    private static String breedFooter(List<Buck> bucks) {
        long breedCount = bucks.stream()
                .map(Buck::getBreed)
                .distinct()
                .count();

        return String.format("%s Breeds", breedCount);
    }
}
