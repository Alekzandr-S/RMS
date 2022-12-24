package com.example.rms.ui.utility;

import com.example.rms.entities.sales.Sales;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableRow;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;

@Route(value = "sales", layout = RootLayout.class)
@PageTitle("SalesView | RMS")
@PermitAll
public class SalesView extends VerticalLayout {
    DatePicker date = new DatePicker();
    TextField quantitySold = new TextField();
    TextField notes = new TextField();
    TextField value = new TextField();

    Table table = new Table();
    Button record = new Button("Add another Sale");
    Binder<Sales> binder = new BeanValidationBinder<>(Sales.class);

    public SalesView() {
        addClassName("sales");
        setSizeFull();

        configureBinder();
        configureButton();

        add(
                configureContents()
        );
    }


    private void configureBinder() {
        binder.forField(date)
                .bind("date");
        binder.forField(notes)
                .bind("notes");
        binder.forField(value)
                .withConverter(new StringToLongConverter("Value needs to be a number"))
                .bind("value");
        binder.forField(quantitySold)
                .withConverter(new StringToIntegerConverter("Quantity needs to be a number"))
                .withValidator(new IntegerRangeValidator("Quantity needs to be at least one", 1, 50))
                .bind("quantitySold");

        binder.bindInstanceFields(this);
    }

    private void configureButton() {
        record.addClickListener(event ->{
            DatePicker date = new DatePicker();
            TextField quantitySold = new TextField();
            TextField notes = new TextField();
            TextField value = new TextField();

            binder.forField(date)
                    .bind("date");
            binder.forField(notes)
                    .bind("notes");
            binder.forField(value)
                    .withConverter(new StringToDoubleConverter("Value needs to be a number"))
                    .bind("value");
            binder.forField(quantitySold)
                    .withConverter(new StringToIntegerConverter("Quantity needs to be a number"))
                    .withValidator(new IntegerRangeValidator("Quantity needs to be at least one", 1, 50))
                    .bind("quantitySold");

            binder.bindInstanceFields(this);

            TableRow tableRow = table.addRow();
            tableRow.addDataCell().add(date);
            tableRow.addDataCell().add(quantitySold);
            tableRow.addDataCell().add(notes);
            tableRow.addDataCell().add(value);


        });
    }

    private Component configureContents() {
        H2 title = new H2("Recording Sales");
        title.addClassName("table-title");

        TableRow headerRow = table.addRow();
        headerRow.addHeaderCell().setText("DATE");
        headerRow.addHeaderCell().setText("QUANTITY SOLD");
        headerRow.addHeaderCell().setText("NOTES");
        headerRow.addHeaderCell().setText("VALUE (K)");

        date.setValue(LocalDate.now());
        quantitySold.setValue("");
        notes.setValue("");
        value.setValue("");

        TableRow detailRow = table.addRow();
        detailRow.addDataCell().add(date);
        detailRow.addDataCell().add(quantitySold);
        detailRow.addDataCell().add(notes);
        detailRow.addDataCell().add(value);

        var vLayout = new VerticalLayout(title, this.table, record);
        vLayout.setSpacing(true);
        vLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vLayout.setAlignSelf(Alignment.START,record);
        return vLayout;
    }
}
