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

   // TODO anchoring whole panel contents (what about making use of ResizablePanel?)
   // TODO top-left alignment by default
   // TODO debug mode - called for every specific built component, propagated recursively, mark method as deprecated for ergonomic purposes
   // TODO check setAlignmentX/Y() not only for labels but also panels, checkboxes etc.. consider calling it via reflection
   // TODO growX() -> spec(Fill.HORIZONTAL/FILL_X) growY() -> spec(Fill.VERTICAL/FILL_Y) -> grow() -> spec(Fill.BOTH/FILL)
   // TODO nextLine() -> addLineBreak() ? - make sure nextLine() always moves to next line - also called consecutively
   // TODO LineSpec, GridSpec, making panel()'s with*Spec extractable to separate method, panelCommand overriding GridSpec builder interface
   // TODO panel().with(container/panel) - if possible deferred usage of panel field
   // TODO panel(), verticalPanel()/columnPanel()/panelOfColumns() or, if above, panel(Orientation.HORIZONTAL/VERTICAL)
   // TODO .withAnchorX(X_LEFT) .withAnchor(X_BOTH, Y_TOP) .withAnchorY(Y_CENTER)
   // TODO line() as first statement hangs
   // TODO component method for wrapping component
   // TODO gap applying to more than one row and no error when components overlaying
   // TODO merge fill and anchor -> alignment CENTER/BOTH - common names for X and Y, also remove withGap* methods?
   // TODO think about applying alignment to labels with preferred size
   // TODO this hangs: add(line().add("Eff:").add(effTextField).add("Disc").add(discTextField).add("Fr:").add(freqTextField))
   // TODO grid() would be good for sections
   private void arrangeComponents()
   {
      buildContent(this, panel().
         specifyDefault(growX().withAnchorX(X_LEFT)).
         withOrientation(Orientation.VERTICAL).
         add(panel().
            withBorder("Conditions").
            specifyColumn(0, spec().withAnchorX(X_RIGHT)).
            specifyColumn(1, spec().withPreferredWidth(70)).
            specifyColumn(2, spec().withAnchorX(X_RIGHT).withWeightX(1.0).withGapX(40)).
            specifyColumn(3, spec().withPreferredWidth(70)).
            specifyColumn(4, spec().withAnchorX(X_RIGHT).withWeightX(1.0).withGapX(40)).
            specifyColumn(5, spec().withPreferredWidth(60)).
            addHeader("Section 1").
            add(line().add("Start Date:").add(startDateTextField).
               add("End Date:").add(endDateTextField).
               add("Freq:").add(freqTextField)).
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
            add(lineWithCombo().add("Criterion 3 Level:").add(criterion3ComboBox).add(criterion3TextField)).
            add(lineWithCombo().add("Criterion 4 Level:").add(criterion4ComboBox).add(criterion4TextField).
               add(roundTripCheckBox, spec().withAnchorX(X_LEFT).withGapX(10).withGridWidthRemaining())
            ).
            addBlank().
            addHeader("Additional Conditions").
            add(lineWithCombo().add("Other 1:").add(other1ComboBox).add(other1TextField).add("ABC:").add(abcTextField)).
            add(lineWithCombo().add("Other 2:").add(other2ComboBox).add(other2TextField)).
            add(lineWithCombo().add("Other 3:").add(other3ComboBox).add(other3TextField)).
            addBlank()
         ).
         add(panel().
            withSpec(growX().withAnchorX(X_LEFT)).
            specifyDefault(spec().withAnchorY(Y_BOTH)).
            add(panel().
               withBorder("Direction").
               specifyDefault(spec().withAnchorX(X_LEFT)).
               withOrientation(Orientation.VERTICAL).
               addAll(directionRadios)
            ).
            add(panel().
               withBorder("Scope").
               specifyDefault(spec().withAnchorX(X_LEFT)).
               withOrientation(Orientation.VERTICAL).
               addAll(scopeRadios)
            ).
            add(panel().
               withBorder("Highlight").
               withSpec(grow().withAnchorX(X_LEFT)).
               specifyColumn(0, spec().withAnchorX(X_RIGHT)).
               specifyColumn(1, spec().withAnchorX(X_LEFT).withWeightX(1.0)).
               specifyDefault(spec().withAnchorX(X_LEFT)).
               add(line().add("Color:").add(colorPicker)).
               add(line().add("Thickness:").add(thicknessComboBox))
            )
         ).
         add(panel().
            withSpec(spec().withAnchor(X_RIGHT, Y_BOTTOM).withWeightY(1.0)). // TODO withFill(bool, bool)
            specifyDefault(spec().withPreferredWidth(66)).
            add(clearButton).add(findAllButton).add(findNextButton).add(closeButton)
         )
      );
   }

   private LineCommand lineWithCombo()
   {
      return line().specifyCell(1, spec().withGridWidth(2).withAnchorX(X_BOTH).withInsetRight(5));
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
      colorPicker = panel().specifyDefault(spec().withInset(1)).addAll(colorPanels).getComponent();
   }

   public static void main(String[] args) throws Exception
   {
//      debug();
//      UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new RichFormDemo2();
   }
}
