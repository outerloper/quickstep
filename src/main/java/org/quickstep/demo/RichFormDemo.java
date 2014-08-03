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

   JButton newButton = new JButton("New");
   JButton deleteButton = new JButton("Delete");
   JButton editButton = new JButton("Edit");
   JButton saveButton = new JButton("Save");
   JButton cancelButton = new JButton("Cancel");

   public RichFormDemo()
   {
      arrangeComponents();

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setVisible(true);
   }

   private void arrangeComponents()
   {
      buildContent(this, panel().
         add(peopleList, specWithFill().withGridHeightRemaining()).
         add(panel().
            withSpec(specWithFillX().withAnchorY(AY.TOP)).
            withMaxLineLength(4).
            specifyColumn(0, spec().withAnchorX(AX.RIGHT).withWeightX(0.2)).
            specifyColumn(1, specWithFillX()).
            specifyColumn(2, spec().withAnchorX(AX.RIGHT).withWeightX(0.2)).
            specifyColumn(3, specWithFillX()).
            add("Last name").
            add(lastNameTextField).
            add("First name").
            add(firstNameTextField).
            add("Phone").
            add(phoneTextField).
            add("Email").
            add(emailTextField).
            add("Address 1").
            add(address1TextField, specWithFillX().withGridWidthRemaining()).
            add("Address 2").
            add(address2TextField, specWithFillX().withGridWidthRemaining()).
            add("City").
            add(cityTextField).
            add("Postal Code", spec().withGridHeight(2)).
            add(postalCodeTextField, spec().withGridHeight(2)).
            add("Country").
            add(countryTextField)
         ).
         addLineBreak().
         add(panel().
            withSpec(specWithFillX().withAnchorY(AY.BOTTOM).withGridWidthRemaining()).
            specifyDefault(spec().withPreferredWidth(66)).
            add(newButton).
            add(deleteButton).
            add(editButton).
            add(saveButton).
            add(cancelButton)
         )
      );
   }

   private void arrangeComponents_tryItYourself()
   {
      buildContent(this, panel().
         add(peopleList).
         add("Last name").
         add(lastNameTextField).
         add("First name").
         add(firstNameTextField).
         add("Phone").
         add(phoneTextField).
         add("Email").
         add(emailTextField).
         add("Address 1").
         add(address1TextField).
         add("Address 2").
         add(address2TextField).
         add("City").
         add(cityTextField).
         add("Postal Code").
         add(postalCodeTextField).
         add("Country").
         add(countryTextField).
         add(newButton).
         add(deleteButton).
         add(editButton).
         add(saveButton).
         add(cancelButton)
      );
   }

   public static void main(String[] args) throws Exception
   {
      //      GridBagBuilder.debug();
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new RichFormDemo();
   }
}
