import numpy as np
import random

class NewPixelGenerator:

    c0 = []
    c1 = []

    def __init__(self):
        self._create_c0_set()
        self._create_c1_set()

    def _create_skelton_matrix(self):
        return np.full(shape=(2, 2), fill_value=0.0)

    def _create_combination(self, color, permutation: int):
        matrix = self._create_skelton_matrix()
        if color == "white":
            if permutation == 0:
                matrix[0][1], matrix[1][1] = 1.0, 1.0
                return matrix
            elif permutation == 1:
                matrix[0][0], matrix[1][0] = 1.0, 1.0
                return matrix
            else:
                raise Exception("Unsupported permutation number")
        elif color == 'black':
            if permutation == 0:
                matrix[0][0], matrix[1][1] = 1.0, 1.0
                return matrix
            elif permutation == 1:
                matrix[0][1], matrix[1][0] = 1.0, 1.0
                return matrix
            else:
                raise Exception("Unsupported permutation number")
        else:
            raise Exception("Unsupported color name")

    def _create_c0_set(self):
        combination_1 = self._create_combination(color='white', permutation=0)
        combination_2 = self._create_combination(color='white', permutation=1)
        self.c0 = [combination_1, combination_2]

    def _create_c1_set(self):
        combination_1 = self._create_combination(color='black', permutation=0)
        combination_2 = self._create_combination(color='black', permutation=1)
        self.c1 = [combination_1, combination_2]

    def get_random_c0(self):
        return self.c0[random.randint(0, 1)]

    def get_random_c1(self):
        return self.c1[random.randint(0, 1)]

