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

/**
 * This class is the one used to create a window that allows
 * the user to have an event list.
 */

public class EventListItem extends JPanel {
    private static final long serialVersionUID = 1L;

    private EventInfo event;
    private Controller controller;

    private JLabel emptyThumbLabel, filledThumbLabel, detailsLabel;

    private JFrame parent;

    public EventListItem(Controller controller, EventInfo event, JFrame parent) {
        super();

        this.controller = controller;
        this.event = event;
        this.parent = parent;

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
        
        emptyThumbLabel = new JLabel();
        filledThumbLabel = new JLabel();
        detailsLabel = new JLabel();
        JPanel detailsPanel = new JPanel(new BorderLayout());
        try {
            String file = "images/thumbs-up-empty.png";
            if (controller.getUser() != null) {
                if (controller.getUser().getSavedEvents().contains(event)) {
                    file = "images/thumbs-up.png";
                }
            }
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/thumbs-up-empty.png").getFile())));
            Image image = icon.getImage();
            image = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            emptyThumbLabel = new JLabel(icon);

            ImageIcon icon2 = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/thumbs-up.png").getFile())));
            Image image2 = icon2.getImage();
            image2 = image2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            icon2 = new ImageIcon(image2);
            filledThumbLabel = new JLabel(icon2);

            ImageIcon detailsIcon = new ImageIcon(ImageIO.read(new File(getClass().getClassLoader().getResource("images/details.png").getFile())));
            Image imageD = detailsIcon.getImage();
            imageD = imageD.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            detailsIcon = new ImageIcon(imageD);
            detailsLabel = new JLabel(detailsIcon);            
        } catch (Exception e) {}

        detailsPanel.setBorder(new EmptyBorder(0,0,0,80));
        detailsPanel.add(detailsLabel, BorderLayout.EAST);

        detailsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new EventWindow(controller, event);
                if (controller.getUser() == null && controller.getOrganize() != null) {
                    parent.dispose();
                }
            }
        });

        emptyThumbLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.getUser().getSavedEvents().add(event);
                container.removeAll();
                container.add(filledThumbLabel, BorderLayout.EAST);
                container.add(detailsPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
                controller.attemptNormalUpdate();
            }
        });

        filledThumbLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //controller.getUser().getSavedEvents().remove(event);
                for (int i = 0; i < controller.getUser().getSavedEvents().size(); i++) {
                    if (controller.getUser().getSavedEvents().get(i).getName().equals(event.getName())) {
                        controller.getUser().getSavedEvents().remove(i);
                        break;
                    }
                }
                container.removeAll();
                container.add(emptyThumbLabel, BorderLayout.EAST);
                container.add(detailsPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
                controller.attemptNormalUpdate();
            }
        });

        JPanel interestedPanel = new JPanel();
        interestedPanel.setLayout(new GridLayout(1, 1));
        JLabel interestedLabel = new JLabel(event.getInterested()+" interested");
        interestedPanel.add(interestedLabel);
        interestedPanel.setBorder(new EmptyBorder(0,30,0,0));
        interestedLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        if (controller.getUser() != null && controller.getOrganize() == null) {
            boolean isIncluded = false;
            for (EventInfo saved: controller.getUser().getSavedEvents()) {
                if (saved.getName().equals(event.getName())) {
                    isIncluded = true;
                    break;
                }
            }

            JLabel thumbLabel = isIncluded ? filledThumbLabel : emptyThumbLabel;
            container.add(thumbLabel, BorderLayout.EAST);
            container.add(detailsPanel, BorderLayout.CENTER);
        } else {
            //container.add(interestedPanel, BorderLayout.WEST);
            container.add(detailsPanel, BorderLayout.EAST);
        }

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