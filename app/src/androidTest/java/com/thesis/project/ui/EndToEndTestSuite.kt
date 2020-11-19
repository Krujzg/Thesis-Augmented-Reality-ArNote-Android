package com.thesis.project.ui

import com.thesis.project.ui.arcamera.ArCameraActivityTest
import com.thesis.project.ui.launcher.LauncherActivityTest
import com.thesis.project.ui.login.LoginActivityTest
import com.thesis.project.ui.main.MainActivityTest
import com.thesis.project.ui.register.RegisterActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(ArCameraActivityTest::class,
                    LauncherActivityTest::class,
                    LoginActivityTest::class,
                    MainActivityTest::class,
                    RegisterActivityTest::class)
class EndToEndTestSuite