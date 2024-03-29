cmake_minimum_required(VERSION 3.4.1)
project(sharedLibrary)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c99")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

file(
        GLOB_RECURSE shared_library_sources
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.c
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp)

file(
        GLOB_RECURSE shared_library_headers
        ${CMAKE_CURRENT_SOURCE_DIR}/src/*.h
        ${CMAKE_CURRENT_SOURCE_DIR}/include/*.h
)

add_library(
        ${PROJECT_NAME}
        SHARED
        ${shared_library_sources}
        ${shared_library_headers}
)

target_include_directories(
        ${PROJECT_NAME}
        PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/include/
)