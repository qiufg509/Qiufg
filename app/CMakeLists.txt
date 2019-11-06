
cmake_minimum_required(VERSION 3.4.1)

add_library( andfix
             SHARED

             src/main/cpp/andfix.cpp )

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


target_link_libraries( # Specifies the target library.
                       andfix

                       ${log-lib} )
