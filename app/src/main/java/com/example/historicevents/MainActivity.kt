package com.example.historicevents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // State variables to manage the app's screen flow and quiz data
            var currentScreen by remember { mutableStateOf("welcome") }
            var questionIndex by remember { mutableStateOf(0) }
            var score by remember { mutableStateOf(0) }
            var feedback by remember { mutableStateOf("") }

            // Array of quiz questions and corresponding correct answers
            // 💡 Use Windows Key + . (dot) to bring up emoji keyboard on Windows
            val questions = arrayOf(
                "Nelson Mandela was the president in 1994. 🤵",
                "The Great Wall of China is visible from space. 🌌",
                "Albert Einstein invented the light bulb. 💡",
                "The pyramids in Egypt are over 4,000 years old. ⛰",
                "The first World War started in 1914. 🌏"
            )
            val answers = arrayOf(true, false, false, true, true)

            // Root layout container with padding and background color
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF6F6F6))
                    .padding(24.dp)
            ) {
                val buttonColor = Color(0xFF4CAF50) // Green
                val textColor = Color.White // Text color for buttons

                when (currentScreen) {
                    // Welcome screen with title and start button
                    "welcome" -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = "📚 Welcome to History Flashcards!",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Text(
                                text = "Test your knowledge and improve your history skills!",
                                fontSize = 18.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Button(
                                onClick = { currentScreen = "quiz" },
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Text("Start Quiz", color = textColor)
                            }
                        }
                    }

                    // Quiz screen displaying current question, buttons, feedback, and next navigation
                    "quiz" -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            // Display the current question with its number
                            Text(
                                text = "Question ${questionIndex + 1}:",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Text(
                                text = questions[questionIndex],
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            // True/False answer buttons with even spacing
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { // 💡 Use Windows Key + . (dot) to bring up emoji keyboard on Windows
                                        feedback = if (answers[questionIndex]) "✅ Correct!" else "❌ Incorrect"
                                        if (answers[questionIndex]) score++
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                    modifier = Modifier.width(120.dp)
                                ) {
                                    Text("True", color = textColor)
                                }
                                Button(
                                    onClick = { // 💡 Use Windows Key + . (dot) to bring up emoji keyboard on Windows
                                        feedback = if (!answers[questionIndex]) "✅ Correct!" else "❌ Incorrect"
                                        if (!answers[questionIndex]) score++
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                    modifier = Modifier.width(120.dp)
                                ) {
                                    Text("False", color = textColor)
                                }
                            }
                            // Show feedback message based on the user's answer
                            Text(
                                text = feedback,
                                color = if (feedback.contains("Correct")) Color(0xFF388E3C) else Color(0xFFD32F2F),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            // Navigation to next question or score screen
                            Button(
                                onClick = {
                                    if (questionIndex < questions.lastIndex) {
                                        questionIndex++
                                        feedback = ""
                                    } else {
                                        currentScreen = "score"
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Text(if (questionIndex < questions.lastIndex) "Next Question" else "View Score", color = textColor)
                            }
                        }
                    }

                    // Score screen with result and options to review, restart or exit
                    "score" -> {
                        // Feedback based on score great job else keep practicing
                        val feedbackText = if (score >= 3) "Great job!" else "Keep practicing!"
                        val feedbackColor = if (score >= 3) Color.Green else Color.Red

                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text( // 💡 Use Windows Key + . (dot) to bring up emoji keyboard on Windows
                                text = "🎉 Quiz Completed!",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Text(
                                text = "Your score: $score / ${questions.size}",
                                fontSize = 20.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            // 👇 Text composable for Feedback based on score
                            Text(
                                text = feedbackText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = feedbackColor
                            )

                            Button( // Review Score
                                onClick = { currentScreen = "review" },
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Text("Review Answers", color = textColor)
                            }
                            Button(
                                onClick = {
                                    currentScreen = "welcome"
                                    questionIndex = 0
                                    score = 0
                                    feedback = ""
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Text("Restart Quiz", color = textColor)
                            }
                            Button(
                                onClick = { finish() }, // Exit app
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Text("Exit", color = Color.White)
                            }
                        }
                    }

                    // Review screen to show all questions and correct answers
                    "review" -> {
                        Column(
                            modifier = Modifier.align(Alignment.TopCenter),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // List each question with its correct answer
                            questions.forEachIndexed { index, question ->
                                Text(
                                    text = "Question ${index + 1}: $question\n✔ Correct Answer: ${if (answers[index]) "True" else "False"}",
                                    fontSize = 16.sp
                                )
                            }
                            // Back to Score Page
                            Button(
                                onClick = {
                                    currentScreen = "score"
                                    questionIndex = 0
                                    score = 0
                                    feedback = ""
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor),
                                modifier = Modifier.padding(top = 24.dp)
                            ) {
                                Text("Back to Score Page", color = textColor)
                            }
                        }
                    }
                }
            }
        }
    }
}
