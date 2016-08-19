package com.fagyapong.ktsyntastic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin

class KotlincSyntasticPlugin implements Plugin<Project> {

	public static final String KT_SYNTASTIC_TASK_NAME = "kotlincSyntastic"

	void apply(Project project) {
		project.extensions.create("kotlincSyntastic", KotlincSyntasticExtension)

		project.afterEvaluate {
			def task = project.tasks.create([(Task.TASK_NAME): KT_SYNTASTIC_TASK_NAME,
				  (Task.TASK_GROUP): "vim",
				  (Task.TASK_DESCRIPTION): "Generate syntastic configuration for kotlinc checker",
				  (Task.TASK_TYPE): KotlincSynstastic])

			task.with {
				resolver {
					def cpath = project.files()
					project.plugins.withType(JavaBasePlugin) {
						project?.sourceSets.all {
							cpath += it.output + it.compileClasspath
						}
					}
					cpath
				}
			}
		}
	}
}

class KotlincSyntasticExtension {
	def String prefOutput = ".syntastic_kotlinc_config"
}
