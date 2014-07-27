package org.quickstep;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

import org.easymock.IArgumentMatcher;
import org.junit.*;

import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.util.DebugSupport.gbcEquals;
import static org.quickstep.util.DebugSupport.gbcToString;

public class GridBagBuilderTest
{
   private JPanel panel;

   static class GBCMatcher implements IArgumentMatcher
   {
      GridBagConstraints constraints;

      GBCMatcher(GridBagConstraints constraints)
      {
         this.constraints = constraints;
      }

      @Override
      public boolean matches(Object o)
      {
         return o instanceof GridBagConstraints && gbcEquals(constraints, (GridBagConstraints) o);
      }

      @Override
      public void appendTo(StringBuffer stringBuffer)
      {
         stringBuffer.append(gbcToString(constraints));
      }
   }

   static GBCMatcher gbc(int x, int y, GridBagSpec spec)
   {
      reportMatcher(new GBCMatcher(spec.toConstraints(x, y)));
      return null;
   }

   private static GridBagConstraints anyGbc()
   {
      return anyObject(GridBagConstraints.class);
   }

   private static JComponent anyComponent()
   {
      return anyObject();
   }

   @Before
   public void setUp()
   {
      panel = createMock(JPanel.class);
      panel.setLayout((LayoutManager) anyObject());
   }

   @Test
   public void testAddComponents()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(1, 0, spec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(2, 0, spec().withInsetLeft(5)));

      replay(panel);

      panel(panel).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowWhenBuilderGridWidthIsSet()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(1, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 2, spec().withInset(5, 0, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowWhenNextRowInvoked()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0)));

      replay(panel);

      panel(panel).
         add(new JLabel()).
         nextLine().
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowWhenRemainingGridWidthUsed()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withGridWidthRemaining()));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0)));

      replay(panel);

      panel(panel).
         add(new JLabel(), spec().withGridWidthRemaining()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowOnlyOnceWhenComponentIsLastInRowAndNextRowInvoked()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withGridWidthRemaining()));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(1).
         add(new JLabel(), spec().withGridWidthRemaining()).
         nextLine().
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void specifyCellDefaultsMethodOverridesImplicitDefaultSpecForAddedComponents()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withInset(0, 0, 3, 10)));
      panel.add(anyComponent(), gbc(1, 0, spec().withInset(0, 10, 3, 10).withGridWidthRemaining()));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(3, 0, 3, 10)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(3, 10, 3, 10)));

      replay(panel);

      panel(panel).
         withDefaultSpec(spec().withInset(10, 3)).
         add(new JLabel()).
         add(new JLabel(), spec().withGridWidthRemaining()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void columnSpecOverridesCellDefaults()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(1, 0, spec().withAnchorX(AnchorX.RIGHT).withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withAnchorX(AnchorX.RIGHT).withInset(5, 5, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         withColumnSpec(1, spec().withAnchorX(AnchorX.RIGHT)).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void rowSpecOverridesCellDefaults()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(1, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(20, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(20, 5, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         withRowSpec(1, spec().withInsetTop(20)).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void cellSpecOverridesRowAndColumnSpec()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withInset(10, 10, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         withCellSpec(0, 0, spec().withInsetLeft(10).withInsetTop(10)).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenRowAndLaterColumnIsSpecifiedThenColumnSpecIsMoreImportant()
   {
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0).withInsetBottom(30)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         withRowSpec(1, spec().withInsetBottom(20)).
         withColumnSpec(1, spec().withInsetBottom(30)).
         addBlank().addBlank().addBlank().
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenColumnAndLaterRowIsSpecifiedThenRowSpecIsMoreImportant()
   {
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0).withInsetBottom(30)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         withColumnSpec(1, spec().withInsetBottom(20)).
         withRowSpec(1, spec().withInsetBottom(30)).
         addBlank().addBlank().addBlank().
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void specProvidedForAddOverridesAllOtherSpecsForCurrentCell()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withAnchor(AnchorX.LEFT, AnchorY.TOP)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         withCellSpec(0, 0, spec().withAnchor(AnchorX.RIGHT, AnchorY.BOTTOM)).
         add(new JLabel(), spec().withAnchor(AnchorX.LEFT, AnchorY.TOP)).
         getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridWidth()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withGridWidth(2)));
      panel.add(anyComponent(), gbc(2, 0, spec().withInset(0, 5, 0, 0)));

      replay(panel);

      panel(panel).
         add(new JLabel(), spec().withGridWidth(2)).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridHeight()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withGridHeight(2)));
      panel.add(anyComponent(), gbc(1, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         add(new JLabel(), spec().withGridHeight(2)).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridSize()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withGridSize(2, 2)));
      panel.add(anyComponent(), gbc(2, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(2, 1, spec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 2, spec().withInset(5, 0, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(3).
         add(new JLabel(), spec().withGridSize(2, 2)).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void noMoreComponentsArePlacedAtColumnWithComponentTakingRemainingGridHeight()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(1, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0).withGridHeightRemaining()));
      panel.add(anyComponent(), gbc(0, 2, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(0, 3, spec().withInset(5, 0, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel(), spec().withGridHeightRemaining()).
         add(new JLabel()).
         add(new JLabel()).
         getComponent();

      verify(panel);
   }

   @Test
   public void applyStyleToEachComponentInCollectionAddedToBuilder()
   {
      java.util.List<JCheckBox> checkBoxes = new LinkedList<JCheckBox>();
      for (int i = 0; i < 6; i++)
      {
         checkBoxes.add(new JCheckBox());
      }
      panel.add((JComponent) anyObject(), gbc(0, 0, spec().withAnchorX(AnchorX.LEFT).withInset(0, 30, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(1, 0, spec().withAnchorX(AnchorX.LEFT).withInset(0, 5, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(2, 0, spec().withAnchorX(AnchorX.LEFT).withInset(0, 5, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(0, 1, spec().withAnchorX(AnchorX.LEFT).withInset(5, 30, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(1, 1, spec().withAnchorX(AnchorX.LEFT).withInset(5, 5, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(2, 1, spec().withAnchorX(AnchorX.LEFT).withInset(5, 5, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(3).
         withColumnSpec(0, spec().withAnchorX(AnchorX.RIGHT).withInsetLeft(30)).
         addAll(checkBoxes, spec().withAnchorX(AnchorX.LEFT)).
         getComponent();

      verify(panel);
   }

   @Test
   public void addingBuilderUsesSpecOfThisBuilderToPlaceItsPanel()
   {
      JPanel auxPanel = new JPanel();

      panel.add(eq(auxPanel), gbc(0, 0, grow().withInset(0, 0, 0, 50)));

      replay(panel);

      PanelCommand builder = panel(auxPanel)
         .withSpec(grow());
      panel(panel).
         withCellSpec(0, 0, spec().withInsetRight(50)).
         add(builder).
         getComponent();

      verify(panel);
   }
}
