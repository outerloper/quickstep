package org.quickstep.demo;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.quickstep.*;

import static org.quickstep.GridBagToolKit.*;

public class RichFormDemo2 extends JFrame
{
   JTextField startDateTextField = new JTextField();
   JTextField endDateTextField = new JTextField();
   JTextField freqTextField = new JTextField();
   JTextField criterion1TextField = new JTextField();
   JTextField criterion2TextField = new JTextField();
   JTextField additionalCriterion1TextField = new JTextField();
   JTextField additionalCriterion2TextField = new JTextField();
   JComboBox criterion3ComboBox = new JComboBox();
   JTextField criterion3TextField = new JTextField();
   JComboBox criterion4ComboBox = new JComboBox();
   JTextField criterion4TextField = new JTextField();
   JTextField abcTextField = new JTextField();
   JCheckBox roundTripCheckBox = new JCheckBox("Checkbox option");
   JComboBox other1ComboBox = new JComboBox();
   JTextField other1TextField = new JTextField();
   JComboBox other2ComboBox = new JComboBox();
   JTextField other2TextField = new JTextField();
   JComboBox other3ComboBox = new JComboBox();
   JTextField other3TextField = new JTextField();
   List<JRadioButton> directionRadios = Arrays.asList(new JRadioButton("Up"), new JRadioButton("Down"));
   List<JRadioButton> scopeRadios = Arrays.asList(new JRadioButton("From cursor"), new JRadioButton("Entire scope"));
   JComponent colorPicker;
   JComboBox thicknessComboBox = new JComboBox<String>(new String[]{"1", "2", "3", "4", "5"});
   JButton clearButton = new JButton("Clear");
   JButton findAllButton = new JButton("Find All");
   JButton findNextButton = new JButton("Find Next");
   JButton closeButton = new JButton("Close");

   public RichFormDemo2() throws HeadlessException
   {
      buildComponents();
      arrangeComponents();
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   private void arrangeComponents()
   {
      buildContent(this, vpanel().
         withDefault(specWithFillX()).
         add(panel().
            withBorder("Conditions").
            withGrid(getGridSpec()).
            addHeader("Section 1").
            add(line().
               add("Start Date:").add(startDateTextField).
               add("End Date:").add(endDateTextField).add("Freq:").add(freqTextField)
            ).
            addHeader("Section 2").
            add(line().
               add("Criterion 1:").add(criterion1TextField).
               add("Criterion 2:").add(criterion2TextField)
            ).
            addHeader("Section 3").
            add(line().
               add("Additional Criterion 1:").add(additionalCriterion1TextField).
               add("Additional Criterion 2:").add(additionalCriterion2TextField)
            ).
            addHeader("Section 4").
            add(lineWithCombo().add("Criterion 3 Level:").add(criterion3ComboBox).add(criterion3TextField)).
            add(lineWithCombo().add("Criterion 4 Level:").add(criterion4ComboBox).add(criterion4TextField).
               add(roundTripCheckBox, spec().withInsetLeft(5).withAnchorX(AX.LEFT).withGridWidthRemainder())
            ).
            addHeader("Additional Conditions").
            add(lineWithCombo().add("Other 1:").add(other1ComboBox).add(other1TextField).add("ABC:").add(abcTextField)).
            add(lineWithCombo().add("Other 2:").add(other2ComboBox).add(other2TextField)).
            add(lineWithCombo().add("Other 3:").add(other3ComboBox).add(other3TextField))
         ).
         add(panel().
            with(specWithFillX()).
            withDefault(spec().withAnchorY(AY.BOTH)).
            add(vpanel().
               withBorder("Direction").
               addAll(directionRadios)
            ).
            add(vpanel().
               withBorder("Scope").
               addAll(scopeRadios)
            ).
            add(panel().
               with(specWithFill()).
               withBorder("Highlight").
               withColumn(0, spec().withAnchorX(AX.RIGHT)).
               withColumn(1, spec().withWeightX(1.0)).
               add(line().add("Color:").add(colorPicker)).
               add(line().add("Thickness:").add(thicknessComboBox))
            )
         ).
         add(panel().
            with(spec().withAnchor(AX.RIGHT, AY.BOTTOM).withWeightY(1.0)).
            withDefault(spec().withPreferredWidth(66)).
            add(seq(clearButton, findAllButton, findNextButton, closeButton))
         )
      );
   }

   private GridSpec getGridSpec()
   {
      return gridSpec().
         withColumn(0, spec().withAnchorX(AX.RIGHT)).
         withColumn(1, spec().withPreferredWidth(70)).
         withColumn(2, spec().withAnchorX(AX.RIGHT).withWeightX(1.0).withInsetLeft(40)).
         withColumn(3, spec().withPreferredWidth(70)).
         withColumn(4, spec().withAnchorX(AX.RIGHT).withWeightX(1.0).withInsetLeft(40)).
         withColumn(5, spec().withPreferredWidth(60));
   }

   private static LineCommand lineWithCombo()
   {
      return line().withCell(1, spec().withGridWidth(2).withAnchorX(AX.BOTH));
   }

   private void buildComponents()
   {
      int n = 5;
      List<JPanel> colorPanels = new LinkedList<JPanel>();
      Color[] colors = new Color[]{Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA, Color.CYAN};
      for (int i = 0; i < n; i++)
      {
         JPanel colorPanel = new JPanel();
         colorPanel.setPreferredSize(new Dimension(18, 18));
         colorPanel.setBackground(colors[i]);
         colorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         colorPanels.add(colorPanel);
      }
      colorPicker = panel().withDefault(spec().withInset(1)).addAll(colorPanels).getComponent();
   }

   public static void main(String[] args) throws Exception
   {
//      debug();
//      UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new RichFormDemo2();
   }
}
