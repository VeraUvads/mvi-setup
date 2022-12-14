package com.github.verauvads.mvisetup.recipies

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import java.io.File

object CustomActivityTemplate : Template {
    val packageName = defaultPackageNameParameter

    private val generateLayout = booleanParameter {
        name = "Поле чтобы указать доп значения"
        default = true
        help = "Подсказка: If true, a layout file will be generated"
    }

    private val isLauncher = booleanParameter {
        name = "Launch activity"
        default = false
        help = "This is the home activity, that is the first activity that is displayed"
    }

    private val addHilt = booleanParameter {
        name = "Add Hilt as a dependency"
        default = true
        help = "If true, adds Hilt dependencies to Gradle"
    }

    private val activityClass = stringParameter {
        name = "Activity Name"
        default = "MainActivity"
        help = "The name of the activity class to create"
        constraints = listOf(Constraint.CLASS, Constraint.UNIQUE, Constraint.NONEMPTY)
        suggest = { layoutToActivity(layoutName.value) }
    }

    private val layoutName = stringParameter {
        name = "Layout Name"
        default = "activity_main"
        help = "The name of the UI layout to create for the activity"
        visible = { generateLayout.value }
        constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
    }

    override val category: Category
        get() = Category.Activity

    override val constraints: Collection<TemplateConstraint>
        get() = emptyList()

    override val description: String
        get() = "Creates a new custom activity for Android"
    override val documentationUrl: String?
        get() = null

    override val formFactor: FormFactor
        get() = FormFactor.Mobile

    override val minSdk: Int
        get() = 24
    override val name: String
        get() = "Custom Activity"
    override val recipe: Recipe
        get() = {
            customActivityRecipe(
                it as ModuleTemplateData,
                activityClass.value,
                isLauncher.value,
                generateLayout.value,
                addHilt.value,
                layoutName.value,
                packageName.value
            )
        }
    override val uiContexts: Collection<WizardUiContext>
        get() = listOf(
            WizardUiContext.MenuEntry,
            WizardUiContext.NewProjectExtraDetail,
            WizardUiContext.NewProject,
            WizardUiContext.NewModule
        )
    override val widgets: Collection<Widget<*>>
        get() = listOf(
            TextFieldWidget(activityClass),
            CheckBoxWidget(isLauncher),
            CheckBoxWidget(generateLayout),
            CheckBoxWidget(addHilt),
            TextFieldWidget(layoutName),
            PackageNameWidget(packageName),
            LanguageWidget()
        )

    override fun thumb(): Thumb {
        return Thumb { findResource(this.javaClass, File("thumbs/img.png")) }
    }
}
