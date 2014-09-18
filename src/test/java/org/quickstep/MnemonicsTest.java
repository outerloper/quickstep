package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.quickstep.GridBagToolKit.panel;

public class MnemonicsTest
{
   private JPanel panel = createMock(JPanel.class);
   private PanelCommand panelCommand;
   private JComponent component = new JTextField();

   @Before
   public void setUp()
   {
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().on(panel);
   }

   @Test
   public void addingLabelWithMnemonicMarkedAtTheBeginning()
   {
      JLabel label = new JLabel("&Name");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("Name", label.getText());
      assertEquals('N', label.getDisplayedMnemonic());
      assertEquals(0, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void addingLabelWithMnemonicMarkedInTheMiddle()
   {
      JLabel label = new JLabel("New &File");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("New File", label.getText());
      assertEquals('F', label.getDisplayedMnemonic());
      assertEquals(4, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void addingLabelWithMnemonicMarkedAfterTheFirstOccurenceOfALetter()
   {
      JLabel label = new JLabel("New Fil&e");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("New File", label.getText());
      assertEquals('E', label.getDisplayedMnemonic());
      assertEquals(7, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void addingLabelWithMnemonicMarkedAtTheEnd()
   {
      JLabel label = new JLabel("Hel&p");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("Help", label.getText());
      assertEquals('P', label.getDisplayedMnemonic());
      assertEquals(3, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void addingLabelWithLiteralAmpersandAndMnemonicMarkedBeforeAmpersand()
   {
      JLabel label = new JLabel("&Save && Close");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("Save & Close", label.getText());
      assertEquals('S', label.getDisplayedMnemonic());
      assertEquals(0, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void addingLabelWithLiteralAmpersandAndMnemonicMarkedAfterAmpersand()
   {
      JLabel label = new JLabel("Save && &Close");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("Save & Close", label.getText());
      assertEquals('C', label.getDisplayedMnemonic());
      assertEquals(7, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void addingLabelWithLiteralAmpersandAndNoMnemonicMarked()
   {
      JLabel label = new JLabel("Save && Close");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("Save & Close", label.getText());
      assertEquals(0, label.getDisplayedMnemonic());
      assertEquals(-1, label.getDisplayedMnemonicIndex());
      assertNull(label.getLabelFor());
   }

   @Test
   public void addingLabelWithNoMnemonicMarked()
   {
      JLabel label = new JLabel("Name");
      panelCommand.add(label).add(component).getComponent();

      assertEquals("Name", label.getText());
      assertEquals(0, label.getDisplayedMnemonic());
      assertEquals(-1, label.getDisplayedMnemonicIndex());
      assertNull(label.getLabelFor());
   }

   @Test
   public void whenAddingLabelThenComponentThenTheLatterComponentHasNoLabelAssigned()
   {
      JLabel label = new JLabel("&Name");
      panelCommand.add(label).add(component).add(new JTextField()).getComponent();

      assertEquals("Name", label.getText());
      assertEquals('N', label.getDisplayedMnemonic());
      assertEquals(0, label.getDisplayedMnemonicIndex());
      assertSame(component, label.getLabelFor());
   }

   @Test
   public void whenAddingEmptyLabelThenItIsNotAssignedToAComponent() {
      JLabel label = new JLabel();
      panelCommand.add(label).add(component).getComponent();

      assertEquals("", label.getText());
      assertEquals(0, label.getDisplayedMnemonic());
      assertEquals(-1, label.getDisplayedMnemonicIndex());
      assertNull(label.getLabelFor());
   }

   @Test
   public void whenAddingTwoLabelsThen1stLabelIsNotAssignedToThe2ndButHasMnemonicSpecified() {
      JLabel labelA = new JLabel("&A");
      JLabel labelB = new JLabel("&B");
      panelCommand.add(labelA).add(labelB).getComponent();

      assertEquals("A", labelA.getText());
      assertEquals('A', labelA.getDisplayedMnemonic());
      assertEquals(0, labelA.getDisplayedMnemonicIndex());
      assertNull(labelA.getLabelFor());
   }

   @Test
   public void whenAddingTwoLabelsAndAComponentThenOnly2ndLabelIsAssignedToThisComponent() {
      JLabel labelA = new JLabel("&A");
      JLabel labelB = new JLabel("&B");
      panelCommand.add(labelA).add(labelB).add(component).getComponent();

      assertEquals("A", labelA.getText());
      assertEquals('A', labelA.getDisplayedMnemonic());
      assertEquals(0, labelA.getDisplayedMnemonicIndex());
      assertNull(labelA.getLabelFor());

      assertEquals("B", labelB.getText());
      assertEquals('B', labelB.getDisplayedMnemonic());
      assertEquals(0, labelB.getDisplayedMnemonicIndex());
      assertSame(component, labelB.getLabelFor());
   }

   @Test
   public void whenAddingLabelAssignedToAComponentThenThisComponentIsNotOverwritten() {
      JLabel label = new JLabel("&A");
      JTextField component2 = new JTextField();
      label.setLabelFor(component2);

      panelCommand.add(label).add(component).add(component2).getComponent();

      assertSame(component2, label.getLabelFor());
   }

   @Test
   public void addButtonWithMnemonic() {
      JButton button = new JButton("&Save");

      panelCommand.add(button).getComponent();

      assertEquals("Save", button.getText());
      assertEquals('S', button.getMnemonic());
      assertEquals(0, button.getDisplayedMnemonicIndex());
   }

   @Test
   public void addButtonWithoutMnemonic() {
      JButton button = new JButton("Save");

      panelCommand.add(button).getComponent();

      assertEquals("Save", button.getText());
      assertEquals(0, button.getMnemonic());
      assertEquals(-1, button.getDisplayedMnemonicIndex());
   }
}
