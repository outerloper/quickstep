package org.quickstep.demo;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import org.quickstep.*;
import org.quickstep.command.*;
import org.quickstep.spec.CellSpec;

import static javax.swing.UIManager.*;
import static org.quickstep.GridBagToolKit.*;

public class CustomCommandsDemo
{
   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      JPanel customPanel = new JPanel();
      customPanel.setBackground(Color.GREEN);

      buildContent(
         frame, panel()
            .withContentAnchor(A.BOTH)
            .withDirection(Direction.TOP_TO_BOTTOM)
            .add(toolbar()
                    .add(new JButton(getIcon("FileChooser.newFolderIcon")))
                    .add(new JButton(getIcon("FileChooser.upFolderIcon")))
                    .addSeparator()
                    .add(new JButton(getIcon("FileChooser.detailsViewIcon")))
                    .add(new JButton("Details..."))
            )
            .add(tabs()
                    .withSpec(spec().withPreferredSize(400, 200))
                    .add(tab("First").add("First tab content"))
                    .add(tab("Second")
                            .withContentAnchor(A.BOTH)
                            .add(horizontalSplit()
                                    .withFirst(panel().add("Second tab left content"))
                                    .withSecond(panel().add("Second tab right content"))
                            )
                    )
                    .add(tab("Third")
                            .withContentAnchor(A.BOTH)
                            .add(verticalSplit()
                                    .withResizeWeight(0.67)
                                    .withFirst(panel().add("Third tab top content"))
                                    .withSecond(panel().add("Third tab bottom content"))
                            )
                    )
                    .add(tab("Fourth")
                            .on(customPanel)
                            .add("Fourth tab content")
                    )
                    .add(tab("Details")
                            .withIcon(getIcon("FileChooser.detailsViewIcon"))
                            .add("<html><i>Details</i> tab content</html>")
                    )
            )
            .add(buttons()
                    .add(new JButton("OK"))
                    .add(new JButton("Cancel"))
                    .add(new JButton("Help"))
            )
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }

   public static ToolBarCommand toolbar()
   {
      return new ToolBarCommand();
   }

   public static TabsCommand tabs()
   {
      return new TabsCommand();
   }

   public static TabCommand tab(String title)
   {
      return new TabCommand(title);
   }

   public static ButtonsCommand buttons()
   {
      return new ButtonsCommand();
   }

   public static SplitCommand horizontalSplit()
   {
      return new SplitCommand(false);
   }

   public static SplitCommand verticalSplit()
   {
      return new SplitCommand(true);
   }

   static class ToolBarCommand extends GridContainerCommand<JToolBar, ToolBarCommand>
   {
      @Override
      public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
      {
         withContentAnchorX(AX.LEFT);
         return super.getComponent(parentDirection, parentFactory);
      }

      @Override
      protected JToolBar createDefaultContainer(ComponentFactory factory)
      {
         return new JToolBar();
      }

      @Override
      protected CellSpec getDefaultSpec(Direction parentDirection)
      {
         return spec().withWeightY(0.0);
      }
   }

   static class TabsCommand extends AbstractComponentCommand<TabsCommand>
   {
      private final List<TabCommand> tabs = new LinkedList<TabCommand>();

      @Override
      public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
      {
         JTabbedPane result = new JTabbedPane();
         int i = 0;
         for (TabCommand tab : tabs)
         {
            result.add(tab.getTitle(), panel().withContentAnchor(A.BOTH).add(tab).getComponent(parentDirection, parentFactory));
            if (tab.getIcon() != null)
            {
               result.setIconAt(i, tab.getIcon());
            }
            i++;
         }
         return result;
      }

      public TabsCommand add(TabCommand tab)
      {
         tabs.add(tab);
         return this;
      }
   }

   static class TabCommand extends GridContainerCommand<JPanel, TabCommand>
   {
      private String title;
      private Icon icon;

      public TabCommand(String title)
      {
         this.title = title;
      }

      String getTitle()
      {
         return title;
      }

      Icon getIcon()
      {
         return icon;
      }

      public TabCommand withIcon(Icon icon)
      {
         this.icon = icon;
         return this;
      }

      @Override
      protected CellSpec getDefaultSpec(Direction parentDirection)
      {
         return spec().withInset(5);
      }

      @Override
      protected JPanel createDefaultContainer(ComponentFactory factory)
      {
         return factory.createPanel();
      }
   }

   static class ButtonsCommand extends AbstractComponentCommand<ButtonsCommand>
   {
      List<JButton> buttons = new LinkedList<JButton>();

      public ButtonsCommand add(JButton button)
      {
         buttons.add(button);
         return this;
      }

      @Override
      public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
      {
         return panel().withContentAnchorX(AX.RIGHT).add(buttons, spec().withSizeGroup(0)).getComponent(parentDirection, parentFactory);
      }

      @Override
      protected CellSpec getDefaultSpec(Direction parentDirection)
      {
         return spec().withWeightY(0.0);
      }
   }

   static class SplitCommand extends AbstractComponentCommand<SplitCommand>
   {
      private PanelCommand first;
      private PanelCommand second;

      private boolean vertical = false;
      private double resizeWeight = 0.5;

      SplitCommand(boolean vertical)
      {
         this.vertical = vertical;
      }

      @Override
      public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
      {
         JSplitPane result = new JSplitPane(vertical ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT);
         if (first != null)
         {
            result.setTopComponent(first.getComponent(parentDirection, parentFactory));
         }
         if (second != null)
         {
            result.setBottomComponent(second.getComponent(parentDirection, parentFactory));
         }
         result.setResizeWeight(resizeWeight);
         return result;
      }

      public SplitCommand withResizeWeight(double resizeWeight)
      {
         this.resizeWeight = resizeWeight;
         return this;
      }

      public SplitCommand withFirst(PanelCommand first)
      {
         this.first = first;
         return this;
      }

      public SplitCommand withSecond(PanelCommand second)
      {
         this.second = second;
         return this;
      }
   }
}