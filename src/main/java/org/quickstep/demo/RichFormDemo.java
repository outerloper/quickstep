package org.quickstep.demo;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class RichFormDemo extends JFrame
{
   JList<String> peopleList = new JList<String>(new String[]{
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
      arrangeComponentsUsingLineWraps();
//      arrangeComponentsUsingExplicitLines();
//      arrangeComponentsPlayWithItYourself();

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setVisible(true);
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
                    .add("Last name").add(lastNameTextField)
                    .add("First name").add(firstNameTextField)
                    .add("Phone").add(phoneTextField)
                    .add("Email").add(emailTextField)
                    .add("Address 1").add(address1TextField)
                    .add("Address 2").add(address2TextField)
                    .add("City").add(cityTextField)
                    .add("Postal Code", spec().withGridHeight(2)).add(postalCodeTextField, spec().withGridHeight(2))
                    .add("Country").add(countryTextField)
            )
            .addLineBreak()
            .add(panel()
                    .withContentAnchorY(AY.BOTTOM)
                    .withDefault(spec().withSizeGroup(0))
                    .add(seq(newButton, deleteButton, editButton, saveButton, cancelButton))
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
                    .add(line().add("Last name").add(lastNameTextField).add("First name").add(firstNameTextField))
                    .add(line().add("Phone").add(phoneTextField).add("Email").add(emailTextField))
                    .add(line().add("Address 1").add(address1TextField, spec().withGridWidthRemainder()))
                    .add(line().add("Address 2").add(address2TextField, spec().withGridWidthRemainder()))
                    .add(line().add("City").add(cityTextField)
                            .add("Postal Code", spec().withGridHeight(2))
                            .add(postalCodeTextField, spec().withGridHeight(2)))
                    .add(line().add("Country").add(countryTextField))
            )
            .addLineBreak()
            .add(panel()
                    .withContentAnchorY(AY.BOTTOM)
                    .withDefault(spec().withSizeGroup(0))
                    .add(seq(newButton, deleteButton, editButton, saveButton, cancelButton))
            )
      );
   }

   private void arrangeComponentsPlayWithItYourself()
   {
      buildContent(
         this, panel()
            .add(peopleList)
            .add("Last name").add(lastNameTextField)
            .add("First name").add(firstNameTextField)
            .add("Phone").add(phoneTextField)
            .add("Email").add(emailTextField)
            .add("Address 1").add(address1TextField)
            .add("Address 2").add(address2TextField)
            .add("City").add(cityTextField)
            .add("Postal Code").add(postalCodeTextField)
            .add("Country").add(countryTextField)
            .add(newButton)
            .add(deleteButton)
            .add(editButton)
            .add(saveButton)
            .add(cancelButton)
      );
   }

   public static void main(String[] args)
   {
//      debug();
      DemoUtils.setSystemLookAndFeel();
      new RichFormDemo();
   }
}
