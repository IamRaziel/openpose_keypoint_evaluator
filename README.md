# openpose_keypoint_evaluator

With this Evaluator the output .json files from [OpenPose](https://cmu-perceptual-computing-lab.github.io/openpose/web/html/doc/) can be evaluated to diferent types of graphics. 
Important: The only type of keypoints the program will generate are the Body_25 pose_keypoints_2d.

The actual version can generate a
  - bar chard
  - point clouds
  - line chard

To launch the program just download a builded version from the [Build](https://github.com/IamRaziel/openpose_keypoint_evaluator/tree/main/Build) folder.
Start it from the console with java -jar {jar-Name} --path {directory-path}.
If you dont add the argument --path, then it will ask you to enter it again. The path is the path to the directory, in which the json files are stored.
