cmake_minimum_required(VERSION 3.9)
project(generator)

set(CMAKE_CXX_STANDARD 17)

add_executable(generator generator.cpp)


project(engine)

find_package(OpenGL REQUIRED)
find_package(GLUT REQUIRED)
add_executable(engine engine.cpp)

target_link_libraries(engine ${OPENGL_LIBRARIES} ${GLUT_LIBRARY})
