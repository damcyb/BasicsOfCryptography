import numpy as np
from NewPixelGenerator import NewPixelGenerator

class Encoder:

    _generator = NewPixelGenerator()

    def encode(self, image: np.ndarray):
        sub_image_left, sub_image_right = self._create_shares(image, height=image.shape[0], width=image.shape[1])
        return sub_image_left, sub_image_right

    def decode(self, left_img, right_img):
        combined = np.add(left_img, right_img)
        return np.where(combined > 1.0, combined, 1.0)

    def _create_shares(self, image: np.ndarray, height: int, width: int):
        share_left: list = []
        share_right: list = []
        row_left: list = []
        row_right: list = []
        for h in range(height):
            for w in range(width):
                matrix = self._get_matrix(image[h][w])
                row_left.append(matrix[0, :])
                row_right.append(matrix[1, :])
            share_left.append(np.array(row_left).flatten())
            share_right.append(np.array(row_right).flatten())
            row_left = []
            row_right = []

        return share_left, share_right

    def _get_matrix(self, pixel_color):
        if pixel_color.any() == 0.0:
            return self._generator.get_random_c0()
        else:
            return self._generator.get_random_c1()

