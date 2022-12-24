package com.example.rms.ui.lists.does;

import com.example.rms.entities.does.Doe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class DoeForm extends FormLayout {
    Doe doe;

    TextField breed = new TextField("Breed");
    TextField cageRef = new TextField("Cage No");
    TextField weight = new TextField("Weight(Kg)");
    Checkbox mated = new Checkbox("Mated");
    Checkbox pregnant = new Checkbox("Pregnant");

    Binder<Doe> binder = new BeanValidationBinder<>(Doe.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public DoeForm() {
        addClassName("doe-form");
        binder.bindInstanceFields(this);

        add(
                cageRef,
                breed,
                mated,
                pregnant,
                weight,
                createButtonLayout()
        );
    }

    public void setDoe(Doe doe) {
        this.doe = doe;
        binder.readBean(doe);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(buttonClickEvent -> fireEvent(
                new DeleteEvent(this, doe)));
        cancel.addClickListener(event -> fireEvent(
                new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(doe);
            fireEvent(new SaveEvent(this, doe));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class DoeFormEvent extends ComponentEvent<DoeForm> {
        private Doe doe;

        protected DoeFormEvent(DoeForm source, Doe doe) {
            super(source, false);
            this.doe = doe;
        }

        public Doe getDoe() {
            return doe;
        }
    }

    public static class SaveEvent extends DoeFormEvent {
        SaveEvent(DoeForm source, Doe doe) {
            super(source,doe);
        }
    }

    public static class DeleteEvent extends DoeFormEvent {
        DeleteEvent(DoeForm source, Doe doe) {
            super(source, doe);
        }
    }

    public static class CloseEvent extends DoeFormEvent {
        CloseEvent(DoeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration
    addListener(Class<T> eventType,
                ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
