import matplotlib.pyplot as plt
import numpy as np
from NewPixelGenerator import NewPixelGenerator
from Encoder import Encoder
import cv2

if __name__ == '__main__':
    img = plt.imread("./imgs/img.png")
    img = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    img = np.where(img < 0.5, 1.0, 0.0)

    generator = NewPixelGenerator()
    encoder = Encoder()
    sub_img_left, sub_img_right = encoder.encode(img)
    decoded_img = encoder.decode(sub_img_left, sub_img_right)

    plt.subplot(141)
    plt.imshow(img, cmap='gray')
    plt.subplot(142)
    plt.imshow(sub_img_left, cmap='gray')
    plt.subplot(143)
    plt.imshow(sub_img_right, cmap='gray')
    plt.subplot(144)
    plt.imshow(decoded_img, cmap='gray')
    plt.show()