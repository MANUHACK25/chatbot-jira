package com.lodgingApplication.view;
import com.lodgingApplication.domain.services.TicketService;
import com.lodgingApplication.persistence.entity.EstadoTicket;
import com.lodgingApplication.persistence.entity.Ticket;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@org.springframework.stereotype.Component
public class MainView extends VerticalLayout {
    Grid<Ticket> grid = new Grid<>(Ticket.class);
    private TicketService ticketService;

    public MainView(TicketService ticketService){
        this.ticketService = ticketService;
        /*
        NativeLabel label = new NativeLabel("Hola papus");
        Button button = new Button("Haz Click aqui", event -> label.setText("Boton clickeado"));

        DatePicker datePicker = new DatePicker("pick a date");

       // add(new H1("Hello World"), label, button, datePicker);


        //esto hace que esten en el mismo layout horizontal tanto el BUtton como el datepicker
        HorizontalLayout layout = new HorizontalLayout(button, datePicker);
        layout.setDefaultVerticalComponentAlignment(Alignment.END);
        add(layout);

        button.addClickListener(click -> add(new Paragraph("Clicked: " + datePicker.getValue())));

        */
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(grid);
        updateList();



    }
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("idTicket", "titleTicket", "descriptioTicket", "commentaryTicket", "label", "solucion");

    }

    private void updateList() {
        grid.setItems(ticketService.getAll());
}

}

