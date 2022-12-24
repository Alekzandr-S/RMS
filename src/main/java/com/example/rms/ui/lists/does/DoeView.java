package com.example.rms.ui.lists.does;

import com.example.rms.entities.does.Doe;
import com.example.rms.entities.does.DoeService;
import com.example.rms.ui.utility.RootLayout;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

@PageTitle("Does | RMS")
@Route(value = "does", layout = RootLayout.class)
@PermitAll
public class DoeView extends VerticalLayout {
    H2 topLayout = new H2("Does");
    Grid<Doe> grid = new Grid<>(Doe.class,false);
    TextField filterText = new TextField();

    DoeForm form;
    DoeService service;

    public DoeView(DoeService service) {
        this.service = service;
        addClassName("doe-view");
        setSizeFull();

        configureGrid();
        configureForm();
        updateList();
        closeEditor();


        add(topLayout(),getToolbar(),getContent());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setDoe(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllDoes(filterText.getValue()));
    }

    private Component getContent() {
       HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new DoeForm();
        form.setWidth("25em");

        form.addListener(DoeForm.SaveEvent.class, this::saveDoe);
        form.addListener(DoeForm.DeleteEvent.class, this::deleteDoe);
        form.addListener(DoeForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteDoe(DoeForm.DeleteEvent event) {
        service.deleteDoe(event.getDoe());
        updateList();
        closeEditor();
    }
    private void saveDoe(DoeForm.SaveEvent event) {
        service.saveDoe(event.getDoe());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        var streamResource = new StreamResource("does.csv",
                () ->{
                    Stream<Doe> does = grid.getGenericDataView().getItems();
                    StringWriter output = new StringWriter();
                    var beanToCv = new StatefulBeanToCsvBuilder<Doe>(output).build();
                    try {
                        beanToCv.write(does);
                        var contents = output.toString();
                        return new ByteArrayInputStream(contents.getBytes());
                    } catch (CsvDataTypeMismatchException e) {
                        e.printStackTrace();
                        return null;
                    } catch (CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );
        var download = new Anchor(streamResource,"Excel Report");
        Button down = new Button(download);
        filterText.setPlaceholder("Filter by cageRef");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button button = new Button("Add doe");
        button.addClickListener(e -> addDoe());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, button,down);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addDoe() {
        grid.asSingleSelect().clear();
        editDoe(new Doe());
    }

    private void configureGrid() {
        List<Doe> does = service.getAllDoes();
        grid.addClassName("doe-grid");
        grid.setSizeFull();

        grid.addColumn(Doe::getCageRef)
                .setHeader("Cage Ref")
                .setFooter(String.format("%s Total Does", service.countDoes()));
        grid.addColumn(Doe::getBreed)
                .setFooter(breedFooter(does))
                .setHeader("Breed");
        grid.addColumn(Doe::getWeight).setHeader("Weight");
        grid.addColumn(doe -> doe.isPregnant() ? "Yes" : "No")
//                .setTooltipGenerator(Doe::getPregnant)
                .setFooter(pregnancyFooter(does))
                .setHeader("Is Pregnant");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e ->
                editDoe(e.getValue()));
        grid.addThemeVariants(GridVariant.LUMO_COMPACT);
    }

    private void editDoe(Doe doe) {
        if (doe == null) {
            closeEditor();
        } else {
            form.setDoe(doe);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private Component topLayout() {
        topLayout.addClassName("topLayout");
        return topLayout;
    }
//    private Icon createStatusIcon(String status) {
//        boolean isPregnant = "Yes".equals(status);
//        Icon icon;
//        if (isPregnant) {
//            icon = VaadinIcon.CHECK.create();
//            icon.getElement().getThemeList().add("badge success");
//        } else {
//            icon = VaadinIcon.CLOSE_SMALL.create();
//            icon.getElement().getThemeList().add("badge error");
//        }
//        icon.getStyle().set("padding", "var(--lumo-space-xs");
//        return icon;
//    }

    private static String breedFooter(List<Doe> does) {
        long breedCount = does.stream()
                .map(Doe::getBreed)
                .distinct()
                .count();

        return String.format("%s Breeds", breedCount);
    }
    private static String pregnancyFooter(List<Doe> does) {
        long pregnancyCount = does.stream()
                .filter(doe -> doe.isPregnant())
                .count();

        return String.format("%s Pregnant does", pregnancyCount);
    }
}
