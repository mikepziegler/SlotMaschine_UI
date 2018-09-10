package ch.mizye.SlotMaschine;

import java.awt.*;
import javax.swing.*;


public class CGUI extends JFrame {

    private CWheel wheel1, wheel2, wheel3;
    private CDisplay dcapital, dbet;
    private CCapital capital;

    private JButton btnLever, btnPayout, btnCoininput, btnBet;
    private JLabel labelC, labelB;
    private JTextField tfBet;

    private static String OS = System.getProperty("os.name").toLowerCase();

    public CGUI() {
        capital = new CCapital();
        setMinimumSize(new Dimension(650, 300));
        setSize(new Dimension(750, 400));
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        init_Components();

        setVisible(true);
    }

    private void init_Components() {



        if (OS.indexOf("win") >= 0) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("ressources/slotmaschine_icon.jpg")));
        } else if (OS.indexOf("mac") >= 0) {
            Taskbar.getTaskbar().setIconImage(new ImageIcon(getClass().getResource("ressources/slotmaschine_icon.jpg")).getImage());
        }

        dcapital = new CDisplay();
        dbet = new CDisplay();

        wheel1 = new CWheel();
        wheel1.setBackground(Color.WHITE);

        wheel2 = new CWheel();
        wheel2.setBackground(Color.WHITE);

        wheel3 = new CWheel();
        wheel3.setBackground(Color.WHITE);

        btnLever = new JButton();
        btnLever.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pulltheLever();
            }
        });

        btnPayout = new JButton("Auszahlen");
        btnPayout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                payout();
            }
        });

        btnCoininput = new JButton("Einzahlen");
        btnCoininput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                boolean b = true;
                while (b == true) b = !inputMoney();

            }
        });

        btnBet = new JButton("Wetten");
        btnBet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                boolean b = true;
                while (b == true) b = !makeBet();
            }
        });

        labelC = new JLabel(" Kapital: ");
        labelB = new JLabel(" Einsatz: ");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelC)
                                .addComponent(dcapital)
                                .addComponent(btnCoininput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPayout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelB)
                                .addComponent(dbet)
                                .addComponent(btnBet)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(wheel1)
                                .addComponent(wheel2)
                                .addComponent(wheel3)
                                .addComponent(btnLever)
                        )
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelC)
                        .addComponent(dcapital, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPayout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCoininput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelB)
                        .addComponent(dbet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBet)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wheel1)
                        .addComponent(wheel2)
                        .addComponent(wheel3)
                        .addComponent(btnLever, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )

        );
    }



    private void pulltheLever() {
        if (capital.checkCapital()) {
            if (capital.Betisset()) {
                int w1 = wheel1.rollWheel();
                int w2 = wheel2.rollWheel();
                int w3 = wheel3.rollWheel();

                String state = capital.checkSymbols(w1, w2, w3);

                switch(state) {
                    case "all":
                        JOptionPane.showMessageDialog(this, "Sie haben dreimal das gleiche Symbol gezogen");
                        break;
                    case "all7":
                        JOptionPane.showMessageDialog(this, "JACKPOT \n Sie haben dreimal 7 gezogen");
                        break;
                    case "double7":
                        JOptionPane.showMessageDialog(this, "Sie haben zweimal 7 gezogen");
                        break;
                    case "one7":
                        JOptionPane.showMessageDialog(this, "Sie haben einmal 7 gezogen");
                        break;
                    case "null":
                        break;
                    default:
                        break;
                }

                capital.removeBet();
                setDBet();
                setDCapital();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sie haben noch keine \nWette gemacht.");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Sie müssen mindestens \nCHF 0.50 einzahlen \num spielen zu können.");
        }
    }

    private void setDCapital() {
        double value = capital.getCapital();

        dcapital.setDisplay(value);
    }

    private void setDBet() {
        double value = capital.getBet();

        dbet.setDisplay(value);
    }

    private boolean inputMoney() {
        String i = (String) JOptionPane.showInputDialog(
                this,
                "Wieviel möchten Sie einzahlen? \nMindestbetrag: CHF 0.50",
                "Einzahlung",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                1);

        if (i != null) {
            if (!ifdouble(i)) {
                JOptionPane.showMessageDialog(this, "Sie haben keine Nummer eingegeben");
                return false;
            } else {
                double value = Double.parseDouble(i);
                capital.inputMoney(value);
                setDCapital();
            }

        }

        return true;
    }

    private boolean makeBet() {
        String i = (String) JOptionPane.showInputDialog(
                this,
                "Wieviel möchten Sie wetten?",
                "Einzahlung",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                1);

        if (i != null) {
            if (!ifdouble(i)) {
                JOptionPane.showMessageDialog(this, "Sie haben keine Zahl eingegeben.");
                return false;
            } else {
                double value = Double.parseDouble(i);
                if (!capital.setBet(value)) {
                    JOptionPane.showMessageDialog(this, "Sie können keinen Betrag wetten,\nder grösser ist als ihr Kapital.");
                    return false;
                } else {
                    setDBet();
                    setDCapital();
                }
            }
        }

        return true;
    }

    private static boolean ifdouble(String i) {
        try {
            Double.parseDouble(i);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private void payout() {
        capital.payout();
        JOptionPane.showMessageDialog(this, "Sie haben ihren Betrag ausbezahlt \nViel Spass damit");
        setDCapital();
    }

}
