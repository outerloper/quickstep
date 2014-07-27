package org.quickstep.demo;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.quickstep.LineCommand;

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

   // TODO line() as first statement hangs
   // TODO withSpec withCellSpec withColumnSpec withRowSpec withDefaultSpec
   // TODO component method for wrapping component
   // TODO gap applying to more than row and no error when components overlaying
   // TODO merge fill and anchor -> alignment CENTER/BOTH - common names for X and Y
   // TODO think about applying alignment to labels with preferred size
   // TODO this hangs: add(line().add("Eff:").add(effTextField).add("Disc").add(discTextField).add("Fr:").add(freqTextField))
   // TODO grid() would be good for sections
   private void arrangeComponents()
   {
      buildContent(this, panel().
         withDefaultSpec(growX()).
         withOrientation(Orientation.VERTICAL).
         withDefaultSpec(spec().withAnchorX(AnchorX.LEFT)).
         add(panel().
            withBorder("Conditions").
            withColumnSpec(0, spec().withAnchorX(AnchorX.RIGHT)).
            withColumnSpec(1, spec().withPreferredWidth(70)).
            withColumnSpec(2, spec().withAnchorX(AnchorX.RIGHT).withWeightX(1.0).withGapX(40)).
            withColumnSpec(3, spec().withPreferredWidth(70)).
            withColumnSpec(4, spec().withAnchorX(AnchorX.RIGHT).withWeightX(1.0).withGapX(40)).
            withColumnSpec(5, spec().withPreferredWidth(60)).
            addHeader("Section 1").
            add("Start Date:").add(startDateTextField).
            add("End Date:").add(endDateTextField).
            add("Freq:").add(freqTextField).
            addBlank().
            addHeader("Section 2").
            add("Criterion 1:").add(criterion1TextField).
            add("Criterion 2:").add(criterion2TextField).
            addBlank().
            addHeader("Section 3").
            add("Additional Criterion 1:").add(additionalCriterion1TextField).
            add("Additional Criterion 2:").add(additionalCriterion2TextField).
            addBlank().
            addHeader("Section 4").
            add(lineWithCombo("Criterion 3 Level:", criterion3ComboBox, criterion3TextField)).
            add(lineWithCombo("Criterion 4 Level:", criterion4ComboBox, criterion4TextField).
               add(roundTripCheckBox, spec().withAnchorX(AnchorX.LEFT).withGapX(10).withGridWidthRemaining())
            ).
            addBlank().
            addHeader("Additional Conditions").
            add(lineWithCombo("Other 1:", other1ComboBox, other1TextField).add("ABC:").add(abcTextField)).
            add(lineWithCombo("Other 2:", other2ComboBox, other2TextField)).
            add(lineWithCombo("Other 3:", other3ComboBox, other3TextField)).
            addBlank()
         ).
         add(panel().
            withSpec(growX().withAnchorX(AnchorX.LEFT)).
            withDefaultSpec(spec().withFillY()).
            add(panel().
               withBorder("Direction").
               withDefaultSpec(spec().withAnchorX(AnchorX.LEFT)).
               withOrientation(Orientation.VERTICAL).
               addAll(directionRadios)
            ).
            add(panel().
               withBorder("Scope").
               withDefaultSpec(spec().withAnchorX(AnchorX.LEFT)).
               withOrientation(Orientation.VERTICAL).
               addAll(scopeRadios)
            ).
            add(panel().
               withBorder("Highlight").
               withSpec(grow().withAnchorX(AnchorX.LEFT)).
               withColumnSpec(0, spec().withAnchorX(AnchorX.RIGHT)).
               withColumnSpec(1, spec().withAnchorX(AnchorX.LEFT).withWeightX(1.0)).
               withDefaultSpec(spec().withAnchorX(AnchorX.LEFT)).
               add(line().add("Color:").add(colorPicker)).
               add(line().add("Thickness:").add(thicknessComboBox))
            )
         ).
         add(panel().
            withSpec(spec().withAnchor(AnchorX.RIGHT, AnchorY.BOTTOM).withFillX(false).withWeightY(1.0)). // TODO withFill(bool, bool)
            withDefaultSpec(spec().withPreferredWidth(66)).
            add(clearButton).
            add(findAllButton).
            add(findNextButton).
            add(closeButton)
         )
      );
   }

   private LineCommand lineWithCombo(String label, JComboBox combo, JTextField textField)
   {
      return line().
         add(label).
         add(combo, spec().withGridWidth(2).withFillX().withInsetRight(5)).
         add(textField);
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
      colorPicker = panel().addAll(colorPanels, spec().withInset(1)).getComponent();
   }

   public static void main(String[] args) throws Exception
   {
//      debug();
//      UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new RichFormDemo2();
   }
}
