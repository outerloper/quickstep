package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.*;
import static org.quickstep.GridBagToolKit.*;

public class SizeGroupsTest
{
   private JPanel panel = createMock(JPanel.class);
   private PanelCommand panelCommand;
   private JComponent[] components = new JComponent[10];

   @Before
   public void setUp()
   {
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().on(panel);
   }

   private JComponent newComponent(int id, int width, int height)
   {
      JLabel result = new JLabel();
      result.setPreferredSize(new Dimension(width, height));
      components[id] = result;
      return result;
   }

   private void assertComponent(int id, int width, int height)
   {
      Dimension preferredSize = components[id].getPreferredSize();
      assertEquals(width, preferredSize.width);
      assertEquals(height, preferredSize.height);
   }

   @Test
   public void whenAddingComponentsWithoutGroupsAssignedThenPreferredWidthsAndHeightsRemainUnchanged()
   {
      panelCommand
         .add(newComponent(1, 15, 30))
         .add(newComponent(2, 25, 20))
         .add(newComponent(3, 35, 10))
         .getComponent();

      assertComponent(1, 15, 30);
      assertComponent(2, 25, 20);
      assertComponent(3, 35, 10);
   }

   @Test
   public void whenAddingComponentsToSameGroupXThenWidthOfTheWidestComponentIsAppliedToTheGroup()
   {
      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroupX(1))
         .add(newComponent(2, 25, 20), spec().withSizeGroupX(1))
         .add(newComponent(3, 35, 10), spec().withSizeGroupX(1))
         .getComponent();

      assertComponent(1, 35, 30);
      assertComponent(2, 35, 20);
      assertComponent(3, 35, 10);
   }

   @Test
   public void whenAddingComponentsToSameGroupYThenHeightOfTheHighestComponentIsAppliedToTheGroup()
   {
      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroupY(1))
         .add(newComponent(2, 25, 20), spec().withSizeGroupY(1))
         .add(newComponent(3, 35, 10), spec().withSizeGroupY(1))
         .getComponent();

      assertComponent(1, 15, 30);
      assertComponent(2, 25, 30);
      assertComponent(3, 35, 30);
   }

   @Test
   public void whenAddingComponentsToSameGroupThenWidthOfTheWidestAndHeightOfTheHighestComponentIsAppliedToTheGroup()
   {
      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroup(1))
         .add(newComponent(2, 25, 20), spec().withSizeGroup(1))
         .add(newComponent(3, 35, 10), spec().withSizeGroup(1))
         .getComponent();

      assertComponent(1, 35, 30);
      assertComponent(2, 35, 30);
      assertComponent(3, 35, 30);
   }

   @Test
   public void whenComponentsAreAddedToTwoGroupsThenResizingIsAppliedToEachOneSeparately()
   {

      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroupX(0))
         .add(newComponent(2, 25, 20), spec().withSizeGroupX(0))
         .add(newComponent(3, 35, 10), spec().withSizeGroupY(1))
         .add(newComponent(4, 45, 40), spec().withSizeGroupY(1))
         .add(newComponent(5, 55, 50), spec().withSizeGroup(2))
         .add(newComponent(6, 65, 60), spec().withSizeGroup(2))
         .getComponent();

      assertComponent(1, 25, 30);
      assertComponent(2, 25, 20);
      assertComponent(3, 35, 40);
      assertComponent(4, 45, 40);
      assertComponent(5, 65, 60);
      assertComponent(6, 65, 60);
   }

   @Test
   public void whenComponentsWithAndWithoutGroupAreAddedThenThoseWithoutGroupsHaveTheirSizeUnchanged()
   {
      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroup(0))
         .add(newComponent(2, 25, 20), spec().withSizeGroup(0))
         .add(newComponent(3, 35, 10))
         .add(newComponent(4, 45, 40))
         .getComponent();

      assertComponent(1, 25, 30);
      assertComponent(2, 25, 30);
      assertComponent(3, 35, 10);
      assertComponent(4, 45, 40);
   }

   @Test
   public void whenRemovingComponentsFromGroupAssignedInDefaultSpecThenThoseComponentAreNotResized()
   {
      panelCommand
         .withDefault(spec().withSizeGroup(0))
         .add(newComponent(1, 15, 30))
         .add(newComponent(2, 25, 20))
         .add(newComponent(3, 35, 10), spec().withSizeGroupUnassigned())
         .add(newComponent(4, 45, 40), spec().withSizeGroupUnassigned())
         .getComponent();

      assertComponent(1, 25, 30);
      assertComponent(2, 25, 30);
      assertComponent(3, 35, 10);
      assertComponent(4, 45, 40);
   }

   @Test
   public void whenRemovingComponentOnlyFromGroupXAssignedInDefaultSpecThenTheComponentRemainsInGroupYTheSameConversely()
   {
      panelCommand
         .withDefault(spec().withSizeGroup(0))
         .add(newComponent(1, 15, 30))
         .add(newComponent(2, 25, 20))
         .add(newComponent(3, 35, 10), spec().withSizeGroupXUnassigned())
         .add(newComponent(4, 45, 40), spec().withSizeGroupYUnassigned())
         .getComponent();

      assertComponent(1, 45, 30);
      assertComponent(2, 45, 30);
      assertComponent(3, 35, 30);
      assertComponent(4, 45, 40);
   }

   @Test
   public void componentMayBelongToBothXAndYGroupsAndResizingInThoseGroupsWorksIndependently()
   {
      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroupX(0))
         .add(newComponent(2, 25, 20), spec().withSizeGroupX(0).withSizeGroupY(1))
         .add(newComponent(3, 35, 40), spec().withSizeGroupY(1))
         .getComponent();

      assertComponent(1, 25, 30);
      assertComponent(2, 25, 40);
      assertComponent(3, 35, 40);
   }

   @Test
   public void componentMayBelongToBothXAndYGroupsWithTheSameIdAndResizingInThoseGroupsWorksIndependently()
   {
      panelCommand
         .add(newComponent(1, 15, 30), spec().withSizeGroupX(0))
         .add(newComponent(2, 25, 20), spec().withSizeGroup(0))
         .add(newComponent(3, 35, 40), spec().withSizeGroupY(0))
         .getComponent();

      assertComponent(1, 25, 30);
      assertComponent(2, 25, 40);
      assertComponent(3, 35, 40);
   }
}
