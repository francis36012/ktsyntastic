package com.fagyapong.ktsyntastic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin

class KotlincSyntasticPlugin implements Plugin<Project> {

	public static final String KT_SYNTASTIC_TASK_NAME = "kotlincSyntastic"
	public static final String KT_ALE_TASK_NAME = "kotlincAle"

	void apply(Project project) {
		project.extensions.create("kotlincSyntastic", KotlincSyntasticExtension)
		project.extensions.create("kotlincAle", KotlincAleExtension)

		project.afterEvaluate {
			// Syntastic
			def syntasticTask = project.tasks.create([(Task.TASK_NAME): KT_SYNTASTIC_TASK_NAME,
				  (Task.TASK_GROUP): "vim",
				  (Task.TASK_DESCRIPTION): "Generate syntastic configuration for kotlinc checker",
				  (Task.TASK_TYPE): KotlincSynstastic])

			syntasticTask.with {
				classpathResolver {
					def cpath = project.files()
					project.plugins.withType(JavaBasePlugin) {
						project?.sourceSets.all {
							cpath += it.output + it.compileClasspath
						}
					}
					cpath
				}
				sourceSetResolver {
					def srcSets = project.files()
					project.plugins.withType(JavaBasePlugin) {
						project.sourceSets.all {
							srcSets.from(it.kotlin.getSrcDirs().toArray())
						}
					}
					srcSets
				}
			}

			// ALE
			def aleTask = project.tasks.create([(Task.TASK_NAME): KT_ALE_TASK_NAME,
				  (Task.TASK_GROUP): "vim",
				  (Task.TASK_DESCRIPTION): "Generate ale (vim) configuration and module.xml (kotlinc)",
				  (Task.TASK_TYPE): KotlincAle])

			aleTask.with {
				classpathResolver {
					def cpath = project.files()
					project.plugins.withType(JavaBasePlugin) {
						project?.sourceSets.all {
							cpath += it.output + it.compileClasspath
						}
					}
					cpath
				}
				sourceSetResolver {
					def srcSets = project.files()
					project.plugins.withType(JavaBasePlugin) {
						project.sourceSets.all {
							srcSets.from(it.kotlin.getSrcDirs().toArray())
						}
					}
					srcSets
				}
			}
		}
	}
}

class KotlincSyntasticExtension {
	def String prefOutput = ".syntastic_kotlinc_config"
}

class KotlincAleExtension {
	// file containing the vim configuration for the checker
	def String prefVimOutput = ".ale_kotlinc_config"

	// module file for kotlinc (compiler)
	def String prefModuleOutput = "module.xml"

	// directory to use for output in the module file
	def String prefKotlincOutput = "bin"
}
