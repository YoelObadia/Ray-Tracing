This project in Java is a prototype of Ray Tracing.

The project is built in few steps.

The first step was to create 2 packages: primitives and geometries. Primitives contains parameters and functions we will use for the ray tracing in next steps. Geometries contains the definitions of geometries forms for the project with attributes and methods.

The second step is oriented about test cases to check BVA and EP test cases for the function getNormal() of geometries. For this, we created a new package: unittests.

The third step is oriented about the intersections between vectors and geometries. Each geometry has his own implementation to find intersections. We wrote also the test cases to verify the implementations.

The fourth step is the implementation of the camera and view plane with pixels to see a render. After this, test cases are implemented to test the camera.

The fifth step is the begining of render. Colors and geometries appears in the view plane after rays from the camera intersects them. Paremeters are added for differents kinds of lights. The pictures are the successfull results of the tests cases.

The sixth step is divided in few parts. First, we create a new test like the last test from the precedent step but with many colors. Then, we added parameters to obtain a real render in 3D with shadow using the Phong model.

The seventh anf final step is mainly oriented on adjustments about the Phong model and tests to see how pictures are with shadows when geometries are behind another geometries. Transparency, reflection and refraction are added to test this.

Now, anyone can use this beginning of Ray Tracing to create pictures composed from geometries with light , shadows, transparency, reflection, refraction. 
Things to Consider: There are 4 improvements, from a visual perspective, that can be made to this project. These are: Anti-aliasing, Soft Shadows, Depth of Field, Glossy Surface and Diffuse Glass.
There are also technical improvements to be made so that image generation takes less resources and time: Adaptive Super-sampling, Boundary Volume, Regular Grid.

Credits: Yoel Obadia & Shimon Cohen (SHimonCohen2001).
