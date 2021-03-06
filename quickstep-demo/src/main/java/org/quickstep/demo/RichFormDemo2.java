package org.quickstep.demo;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.quickstep.command.LineCommand;
import org.quickstep.spec.CellSpec;
import org.quickstep.spec.GridSpec;

import static org.quickstep.GridBagToolKit.*;

public class RichFormDemo2 extends JFrame
{
   JTextField criterion1TextField = new JTextField();
   JTextField criterion2TextField = new JTextField();
   JTextField criterion3TextField = new JTextField();
   JTextField criterion4TextField = new JTextField();
   JTextField criterion5TextField = new JTextField();
   JTextField criterion6TextField = new JTextField();
   JTextField criterion7TextField = new JTextField();
   JComboBox criterion8ComboBox = new JComboBox();
   JTextField criterion8TextField = new JTextField();
   JComboBox criterion9ComboBox = new JComboBox();
   JTextField criterion9TextField = new JTextField();
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
   JComponent colorPicker = createColorPanel();
   JComboBox thicknessComboBox = new JComboBox(new String[]{"1", "2", "3", "4", "5"});
   JButton clearButton = new JButton("Clear");
   JButton findAllButton = new JButton("Find &All");
   JButton findNextButton = new JButton("Find &Next");
   JButton closeButton = new JButton("&Close");

   public RichFormDemo2() throws HeadlessException
   {
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         this, vpanel()
            .withContentAnchorX(AX.BOTH)
            .withScroll()
            .add(panel()
                    .withBorder("Conditions")
                    .withGrid(getGridSpec())

                    .addHeader("Section 1")
                    .add(line()
                            .add("Criterion &1:", criterion1TextField)
                            .add("Criterion &2:", criterion2TextField)
                            .add("Crit. &3:", criterion3TextField))

                    .addHeader("Section 2")
                    .add(line()
                            .add("Criterion &4:", criterion4TextField)
                            .add("Criterion &5:", criterion5TextField))

                    .addHeader("Section 3")
                    .add(line()
                            .add("Criterion &6:", criterion6TextField)
                            .add("Criterion &7:", criterion7TextField))

                    .addHeader("Section 4")
                    .add(lineWithCombo()
                            .add("Criterion &8 Level:", criterion8ComboBox)
                            .add(criterion8TextField))
                    .add(lineWithCombo()
                            .add("Criterion &9 Level:", criterion9ComboBox)
                            .add(criterion9TextField)
                            .add(roundTripCheckBox, getRoundTripSpec()))

                    .addHeader("Section 5")
                    .add(lineWithCombo()
                            .add("Other 1:", other1ComboBox)
                            .add(other1TextField)
                            .add("ABC:", abcTextField))
                    .add(lineWithCombo()
                            .add("Other 2:", other2ComboBox)
                            .add(other2TextField))
                    .add(lineWithCombo()
                            .add("Other 3:", other3ComboBox)
                            .add(other3TextField))
            )
            .add(panel()
                    .withContentAnchor(A.BOTH)
                    .add(vpanel().withContentAnchorX(AX.LEFT).withBorder("Direction").add(directionRadios))
                    .add(vpanel().withContentAnchorX(AX.LEFT).withBorder("Scope").add(scopeRadios))
                    .add(panel().withContentAnchorX(AX.LEFT).withBorder("Highlight")
                            .withColumn(0, spec().withAnchorX(AX.RIGHT))
                            .add(line().add("Color:", colorPicker))
                            .add(line().add("Thickness:", thicknessComboBox))
                    )
            )
            .add(panel()
                    .withSpec(spec().withAnchor(AX.RIGHT, AY.BOTTOM).withWeightY(1.0))
                    .withDefault(spec().withSizeGroupX(0))
                    .add(Arrays.asList(clearButton, findAllButton, findNextButton, closeButton))
            )
      );

      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
   }

   private static CellSpec getRoundTripSpec()
   {
      return spec().withInsetLeft(5).withAnchorX(AX.LEFT).withGridWidthRemainder();
   }

   private static GridSpec getGridSpec()
   {
      return gridSpec()
         .withColumn(0, spec().withAnchorX(AX.RIGHT))
         .withColumn(1, spec().withPreferredWidth(70))
         .withColumn(2, spec().withAnchorX(AX.RIGHT).withWeightX(1.0).withInsetLeft(40))
         .withColumn(3, spec().withPreferredWidth(70))
         .withColumn(4, spec().withAnchorX(AX.RIGHT).withWeightX(1.0).withInsetLeft(40))
         .withColumn(5, spec().withPreferredWidth(60));
   }

   private static LineCommand lineWithCombo()
   {
      return line().withCell(1, spec().withGridWidth(2).withAnchorX(AX.BOTH));
   }

   private static JPanel createColorPanel()
   {
      List<JPanel> colorPanels = new LinkedList<JPanel>();
      for (Color color : new Color[]{Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA, Color.CYAN})
      {
         JPanel colorPanel = new JPanel();
         colorPanel.setBackground(color);
         colorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         colorPanels.add(colorPanel);
      }
      return buildContent(panel().add(colorPanels, spec().withInset(1).withPreferredSize(18, 18)));
   }

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      RichFormDemo2 demo = new RichFormDemo2();
      demo.setVisible(true);
   }
}
