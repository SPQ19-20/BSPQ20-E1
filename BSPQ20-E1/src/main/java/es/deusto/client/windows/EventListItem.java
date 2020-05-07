package es.deusto.client.windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import es.deusto.client.controller.Controller;
import es.deusto.serialization.EventInfo;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class EventListItem extends JPanel {
    private static final long serialVersionUID = 1L;

    private EventInfo event;
    private Controller controller;

    public EventListItem(Controller controller, EventInfo event) {
        super();

        this.controller = controller;
        this.event = event;

        this.initComponents();
    }

    private void initComponents() {
        this.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), BorderFactory.createMatteBorder(0,0,2,0, Color.LIGHT_GRAY)));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        this.setLayout(new BorderLayout());

        JPanel container = new JPanel(new BorderLayout());

        container.add(initDescriptionPanel(), BorderLayout.SOUTH);
        container.add(initMainPanel(), BorderLayout.CENTER);

        this.add(container, BorderLayout.WEST);
        this.add(initInteractionPanel(), BorderLayout.CENTER);
    }

    private JPanel initInteractionPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(10,30,10,10));
        
        JLabel iconLabel = new JLabel();
        JLabel detailsLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/thumbs-up-empty.png").getFile())));
            Image image = icon.getImage();
            image = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            iconLabel = new JLabel(icon);

            ImageIcon detailsIcon = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/details.png").getFile())));
            Image imageD = detailsIcon.getImage();
            imageD = imageD.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            detailsIcon = new ImageIcon(imageD);
            detailsLabel = new JLabel(detailsIcon);            
        } catch (Exception e) {}

        detailsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new EventWindow(controller, event);
            }
        });

        container.add(iconLabel, BorderLayout.EAST);
        container.add(detailsLabel, BorderLayout.CENTER);

        return container;
    }

    private JPanel initMainPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(10,10,0,0));

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        String name = event.getName();
        if (name.length() > 29) {
            name = name.substring(0, 30) + "...";
        }
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));

        String locationText = "("+event.getCity()+", "+event.getCountry()+")";
        JLabel infoLabel = new JLabel(event.getDate()+" "+locationText);
        Font italic = new Font("Arial", Font.ITALIC, 15);
        infoLabel.setFont(italic);

        JPanel infoPanel = new JPanel(new BorderLayout());
        JPanel namePanel = new JPanel(new BorderLayout());

        infoPanel.setBorder(new EmptyBorder(5,0,0,0));

        infoPanel.add(infoLabel, BorderLayout.WEST);
        namePanel.add(nameLabel, BorderLayout.WEST);

        mainPanel.add(namePanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        container.add(mainPanel, BorderLayout.WEST);

        return container;
    }

    private JPanel initDescriptionPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(20,10,20,0));
        JLabel descLabel = new JLabel(event.getDescription());
        container.add(descLabel, BorderLayout.WEST);
        return container;
    }
}