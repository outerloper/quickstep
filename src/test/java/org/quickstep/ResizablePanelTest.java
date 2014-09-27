package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.quickstep.GridBagToolKit.*;

public class ResizablePanelTest
{
   private JPanel panel = new ResizablePanel();
   private PanelCommand panelCommand;

   @Before
   public void setUp()
   {
      panelCommand = panel().on(panel);
   }

   @Test
   public void whenEmptyResizablePanelThenItsBaselineUnspecifiedAndBehaviorOther()
   {
      ResizablePanel panel = new ResizablePanel();
      assertEquals(-1, panel.getBaseline(20, 30));
      assertEquals(Component.BaselineResizeBehavior.OTHER, panel.getBaselineResizeBehavior());
   }

   @Test
   public void baselineOfResizablePanelIsTakenFromBaselineOfItsFirstComponent()
   {
      panelCommand
         .add(new ComponentMock(10, Component.BaselineResizeBehavior.CONSTANT_ASCENT))
         .add(new ComponentMock(20, Component.BaselineResizeBehavior.CONSTANT_DESCENT))
         .getComponent();
      assertEquals(10, panel.getBaseline(20, 30));
      assertEquals(Component.BaselineResizeBehavior.CONSTANT_ASCENT, panel.getBaselineResizeBehavior());
   }

   @Test
   public void baselineOfResizablePanelWrappingComponentIsTakenFromWrappedComponent()
   {
      ResizablePanel wrapper = ResizablePanel.wrap(new ComponentMock(20, Component.BaselineResizeBehavior.CONSTANT_DESCENT));
      assertEquals(20, wrapper.getBaseline(20, 30));
      assertEquals(Component.BaselineResizeBehavior.CONSTANT_DESCENT, wrapper.getBaselineResizeBehavior());
   }

   @Test
   public void baselineOfResizablePanelIsAwareOfFirstComponentIPad()
   {
      panelCommand
         .add(new ComponentMock(10, Component.BaselineResizeBehavior.CONSTANT_DESCENT), spec().withIPad(30))
         .getComponent();
      assertEquals(25, panel.getBaseline(20, 30));
   }

   @Test
   public void baselineOfResizablePanelIsAwareOfFirstComponentTopInset()
   {
      panelCommand
         .add(new ComponentMock(10, Component.BaselineResizeBehavior.CONSTANT_DESCENT), spec().withInsetTop(30))
         .getComponent();
      assertEquals(40, panel.getBaseline(20, 30));
   }

   @Test
   public void baselineOfNestedResizablePanelsIsTransitive()
   {
      panelCommand
         .add(panel()
                 .add(panel()
                         .add(new ComponentMock(10, Component.BaselineResizeBehavior.CONSTANT_DESCENT))
                 )
         )
         .getComponent();
      assertEquals(10, panel.getBaseline(20, 30));
   }

   @Test
   public void baselineOfNestedResizablePanelsIsAwareOfIPadsAndInsetsOfNestedPanels()
   {
      panelCommand
         .add(panel()
                 .withSpec(spec().withIPadY(30))
                 .add(panel()
                         .withSpec(spec().withInsetTop(20))
                         .add(new ComponentMock(10, Component.BaselineResizeBehavior.CONSTANT_DESCENT))
                 )
         )
         .getComponent();
      assertEquals(45, panel.getBaseline(20, 30));
   }

   private class ComponentMock extends JLabel
   {
      int baseline;
      BaselineResizeBehavior baselineResizeBehavior;

      private ComponentMock(int baseline, BaselineResizeBehavior baselineResizeBehavior)
      {
         this.baseline = baseline;
         this.baselineResizeBehavior = baselineResizeBehavior;
      }

      @Override
      public int getBaseline(int width, int height)
      {
         return baseline;
      }

      @Override
      public BaselineResizeBehavior getBaselineResizeBehavior()
      {
         return baselineResizeBehavior;
      }
   }
}
