package org.quickstep.demo;

import java.util.Arrays;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class RichFormDemo extends JFrame
{
   JList peopleList = new JList(new String[]{
      "Bunny Bugs", "Cat Sylvester", "Coyote Wile E.", "Devil Tasmanian", "Duck Daffy",
      "Fudd Elmer", "Le Pew Pepe", "Martial Marvin"
   });

   JTextField lastNameTextField = new JTextField();
   JTextField firstNameTextField = new JTextField();
   JTextField phoneTextField = new JTextField();
   JTextField emailTextField = new JTextField();
   JTextField address1TextField = new JTextField();
   JTextField address2TextField = new JTextField();
   JTextField cityTextField = new JTextField();
   JTextField postalCodeTextField = new JTextField();
   JTextField countryTextField = new JTextField();

   JButton newButton = new JButton("&New");
   JButton deleteButton = new JButton("&Delete");
   JButton editButton = new JButton("&Edit");
   JButton saveButton = new JButton("&Save");
   JButton cancelButton = new JButton("&Cancel");

   public RichFormDemo()
   {
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      arrangeComponentsUsingLineWraps();
//      arrangeComponentsUsingExplicitLines();
//      arrangeComponentsPlayWithItYourself();

      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
   }

   private void arrangeComponentsUsingLineWraps()
   {
      buildContent(
         this, panel()
            .withContentAnchor(A.BOTH)
            .add(peopleList, spec().withGridHeightRemainder())
            .add(panel()
                    .withLineLength(4)
                    .withContentAnchor(AX.BOTH, AY.TOP)
                    .withColumn(0, spec().withWeight(0.2).withAnchorX(AX.RIGHT))
                    .withColumn(2, spec().withWeight(0.2).withAnchorX(AX.RIGHT))
                    .add("Last name", lastNameTextField)
                    .add("First name", firstNameTextField)
                    .add("Phone", phoneTextField)
                    .add("Email", emailTextField)
                    .add("Address 1", address1TextField)
                    .add("Address 2", address2TextField)
                    .add("City", cityTextField)
                    .add("Postal Code", spec().withGridHeight(2)).add(postalCodeTextField, spec().withGridHeight(2))
                    .add("Country", countryTextField)
            )
            .addLineBreak()
            .add(panel()
                    .withContentAnchorY(AY.BOTTOM)
                    .withDefault(spec().withSizeGroup(0))
                    .add(Arrays.asList(newButton, deleteButton, editButton, saveButton, cancelButton))
            )
      );
   }

   private void arrangeComponentsUsingExplicitLines()
   {
      buildContent(
         this, panel()
            .withContentAnchor(A.BOTH)
            .add(peopleList, spec().withGridHeightRemainder())
            .add(panel()
                    .withContentAnchor(AX.BOTH, AY.TOP)
                    .withColumn(0, spec().withWeight(0.2).withAnchorX(AX.RIGHT))
                    .withColumn(2, spec().withWeight(0.2).withAnchorX(AX.RIGHT))
                    .add(line().add("Last name", lastNameTextField).add("First name", firstNameTextField))
                    .add(line().add("Phone", phoneTextField).add("Email", emailTextField))
                    .add(line().add("Address 1", address1TextField, spec().withGridWidthRemainder()))
                    .add(line().add("Address 2", address2TextField, spec().withGridWidthRemainder()))
                    .add(line().add("City", cityTextField)
                            .add("Postal Code", spec().withGridHeight(2))
                            .add(postalCodeTextField, spec().withGridHeight(2))
                    )
                    .add(line().add("Country", countryTextField))
            )
            .addLineBreak()
            .add(panel()
                    .withContentAnchorY(AY.BOTTOM)
                    .withDefault(spec().withSizeGroup(0))
                    .add(Arrays.asList(newButton, deleteButton, editButton, saveButton, cancelButton))
            )
      );
   }

   private void arrangeComponentsPlayWithItYourself()
   {
      buildContent(
         this, panel()
            .add(peopleList)
            .add("Last name", lastNameTextField)
            .add("First name", firstNameTextField)
            .add("Phone", phoneTextField)
            .add("Email", emailTextField)
            .add("Address 1", address1TextField)
            .add("Address 2", address2TextField)
            .add("City", cityTextField)
            .add("Postal Code", postalCodeTextField)
            .add("Country", countryTextField)
            .add(newButton)
            .add(deleteButton)
            .add(editButton)
            .add(saveButton)
            .add(cancelButton)
      );
   }

   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      RichFormDemo demo = new RichFormDemo();
      demo.setVisible(true);
   }
}
