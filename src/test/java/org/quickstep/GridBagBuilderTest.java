package org.quickstep;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

import org.easymock.IArgumentMatcher;
import org.junit.*;

import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;

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
         build();

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
         build();

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
         build();

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
         build();

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
         build();

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
         specifyCellDefaults(spec().withInset(10, 3)).
         add(new JLabel()).
         add(new JLabel(), spec().withGridWidthRemaining()).
         add(new JLabel()).
         add(new JLabel()).
         build();

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
         specifyColumn(1, spec().withAnchorX(AnchorX.RIGHT)).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         build();

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
         specifyRow(1, spec().withInsetTop(20)).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void cellSpecOverridesRowAndColumnSpec()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withInset(10, 10, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         specifyCell(0, 0, spec().withInsetLeft(10).withInsetTop(10)).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void whenRowAndLaterColumnIsSpecifiedThenColumnSpecIsMoreImportant()
   {
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0).withInsetBottom(30)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         specifyRow(1, spec().withInsetBottom(20)).
         specifyColumn(1, spec().withInsetBottom(30)).
         skip(3).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void whenColumnAndLaterRowIsSpecifiedThenRowSpecIsMoreImportant()
   {
      panel.add(anyComponent(), gbc(1, 1, spec().withInset(5, 5, 0, 0).withInsetBottom(30)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         specifyColumn(1, spec().withInsetBottom(20)).
         specifyRow(1, spec().withInsetBottom(30)).
         skip(3).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void specProvidedForAddOverridesAllOtherSpecsForCurrentCell()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withAnchor(AnchorX.LEFT, AnchorY.TOP)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         specifyCell(0, 0, spec().withAnchor(AnchorX.RIGHT, AnchorY.BOTTOM)).
         add(new JLabel(), spec().withAnchor(AnchorX.LEFT, AnchorY.TOP)).
         build();

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
         build();

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
         build();

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
         build();

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
         build();

      verify(panel);
   }

   @Test
   public void testSkipMethodInOneRowCase()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(2, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(5, 0, spec().withInset(0, 5, 0, 0)));

      replay(panel);

      panel(panel).
         add(new JLabel()).
         skip().
         add(new JLabel()).
         skip().
         skip().
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void testSkipAsFirstDirectiveForBuilder()
   {
      panel.add(anyComponent(), gbc(1, 0, spec().withInset(0, 5, 0, 0)));

      replay(panel);

      panel(panel).
         skip().
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void testMultipleSkipAsFirstDirectiveForBuilder()
   {
      panel.add(anyComponent(), gbc(3, 0, spec().withInset(0, 5, 0, 0)));

      replay(panel);

      panel(panel).
         skip(3).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void testMultipleSkipMethodInOneRowCase()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(2, 0, spec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(5, 0, spec().withInset(0, 5, 0, 0)));

      replay(panel);

      panel(panel).
         add(new JLabel()).
         skip(1).
         add(new JLabel()).
         skip(2).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void testSkipMethodInMultipleRowsCase()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 2, spec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(1, 3, spec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 5, spec().withInset(5, 0, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         add(new JLabel()).
         skip().
         add(new JLabel()).
         skip().
         skip().
         add(new JLabel()).
         skip().
         add(new JLabel()).
         skip().
         skip().
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void testMultipleSkipMethodInMultipleRowsCase()
   {
      panel.add(anyComponent(), gbc(0, 0, spec()));
      panel.add(anyComponent(), gbc(0, 1, spec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 2, spec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(1, 3, spec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 5, spec().withInset(5, 0, 0, 0)));

      replay(panel);

      panel(panel).
         withMaxLineLength(2).
         add(new JLabel()).
         skip(1).
         add(new JLabel()).
         skip(2).
         add(new JLabel()).
         skip(1).
         add(new JLabel()).
         skip(2).
         add(new JLabel()).
         build();

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
         specifyColumn(0, spec().withAnchorX(AnchorX.RIGHT).withInsetLeft(30)).
         add(checkBoxes, spec().withAnchorX(AnchorX.LEFT)).
         build();

      verify(panel);
   }

   @Test
   public void addingBuilderUsesSpecOfThisBuilderToPlaceItsPanel()
   {
      JPanel auxPanel = new JPanel();

      panel.add(eq(auxPanel), gbc(0, 0, grow().withInset(0, 0, 0, 50)));

      replay(panel);

      GridBagBuilder builder = panel(auxPanel)
         .withSpec(grow());
      panel(panel).
         specifyCell(0, 0, spec().withInsetRight(50)).
         add(builder).
         build();

      verify(panel);
   }

   @Test
   public void derivedPanelPreservesSpecOfItsTemplate()
   {
      JPanel auxPanel = new JPanel();

      panel.add((JComponent) anyObject(), gbc(0, 0, grow().withInset(0, 0, 0, 50)));

      replay(panel);

      GridBagBuilder template = panel(auxPanel).
         withSpec(grow());
      panel(panel).
         specifyCell(0, 0, spec().withInsetRight(50)).
         add(template.derive()).
         build();

      verify(panel);
   }

   @Test
   public void derivedPanelPreservesGridSpecsAndGridWidthOfItsTemplate()
   {
      panel.add((JComponent) anyObject(), gbc(0, 0, spec().withAnchor(AnchorX.RIGHT, AnchorY.BOTTOM).withInset(10, 10, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(1, 0, spec().withAnchor(AnchorX.LEFT, AnchorY.BOTTOM).withInset(0, 5, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(0, 1, spec().withAnchor(AnchorX.RIGHT, AnchorY.TOP).withInset(5, 0, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(1, 1, spec().withAnchor(AnchorX.LEFT, AnchorY.TOP).withInset(5, 5, 0, 0)));

      replay(panel);

      GridBagBuilder template = panel().
         withMaxLineLength(2).
         specifyCell(0, 0, spec().withInsetTop(10).withInsetLeft(10)).
         specifyRow(0, spec().withAnchorY(AnchorY.BOTTOM)).
         specifyColumn(0, spec().withAnchorX(AnchorX.RIGHT)).
         specifyCellDefaults(spec().withAnchor(AnchorX.LEFT, AnchorY.TOP));
      template.derive(panel).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         build();

      verify(panel);
   }

   @Test
   public void derivedPanelPreservesGridSizesToEndOfItsTemplate()
   {
      panel.add((JComponent) anyObject(), gbc(0, 0, spec().withGridHeightRemaining().withInset(0, 0, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(1, 0, spec().withGridWidthRemaining().withInset(0, 5, 0, 0)));
      panel.add((JComponent) anyObject(), gbc(1, 1, spec().withInset(5, 5, 0, 0)));

      replay(panel);

      GridBagBuilder template = panel().
         specifyCell(0, 0, spec().withGridHeightRemaining()).
         specifyCell(1, 0, spec().withGridWidthRemaining());
      template.derive(panel).
         add(new JLabel()).
         add(new JLabel()).
         add(new JLabel()).
         build();

      verify(panel);
   }
}
