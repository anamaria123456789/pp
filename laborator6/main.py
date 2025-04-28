from abc import ABC, abstractmethod
import os


class GenericFile(ABC):
    @abstractmethod
    def get_path(self):
        raise NotImplementedError("get_path not implemented")

    @abstractmethod
    def get_freq(self):
        raise NotImplementedError("get_freq not implemented")


class TextASCII(GenericFile):
    def __init__(self, path_absolut, frecvente):
        self.path_absolut = path_absolut
        self.frecvente = frecvente

    def get_path(self):
        return self.path_absolut

    def get_freq(self):
        return self.frecvente


class TextUNICODE(GenericFile):
    def __init__(self, path_absolut, frecvente):
        self.path_absolut = path_absolut
        self.frecvente = frecvente

    def get_path(self):
        return self.path_absolut

    def get_freq(self):
        return self.frecvente


class Binary(GenericFile):
    def __init__(self, path_absolut, frecvente):
        self.path_absolut = path_absolut
        self.frecvente = frecvente

    def get_path(self):
        return self.path_absolut

    def get_freq(self):
        return self.frecvente


class XMLFile(TextASCII):
    def __init__(self, path_absolut, frecvente, first_tag):
        super().__init__(path_absolut, frecvente)
        self.first_tag = first_tag

    def get_first_tag(self):
        return self.first_tag


class BMP(Binary):
    def __init__(self, path_absolut, frecvente, width, height, bpp):
        super().__init__(path_absolut, frecvente)
        self.width = width
        self.height = height
        self.bpp = bpp

    def show_info(self):
        print(f"BMP Image: {self.width}x{self.height}, {self.bpp} bits per pixel")



def is_ascii_xml(content):
    try:
        start = content[:50].decode('ascii', errors='ignore').lower()
        return '<?xml' in start
    except UnicodeDecodeError:
        return False


def is_unicode(content):
    if len(content) < 2:
        return False
    bom = content[:2]
    return bom in [b'\xff\xfe', b'\xfe\xff'] or not content[:100].isascii()


def is_bmp(content):
    if len(content) < 54 or content[:2] != b'BM':
        return False, 0, 0, 0
    width = int.from_bytes(content[18:22], byteorder='little')
    height = int.from_bytes(content[22:26], byteorder='little')
    bpp = int.from_bytes(content[28:30], byteorder='little')
    return True, width, height, bpp



def calc_freq(content, max_bytes=100000):
    frecvente = {}
    for byte in content[:max_bytes]:
        frecvente[byte] = frecvente.get(byte, 0) + 1
    return frecvente



def scan_directory(root_dir):
    xml_ascii_files = []
    unicode_files = []
    bmp_files = []

    for root, _, files in os.walk(root_dir):
        for file in files:
            file_path = os.path.join(root, file)
            if not os.path.isfile(file_path):
                continue

            with open(file_path, 'rb') as f:
                content = f.read()
                frecvente = calc_freq(content)

                if is_ascii_xml(content):
                    first_tag_start = content.find(b'<') + 1
                    first_tag_end = content.find(b'>')
                    first_tag = content[first_tag_start:first_tag_end].decode('ascii', 'ignore')
                    xml_ascii_files.append(XMLFile(file_path, frecvente, first_tag))

                elif is_unicode(content):
                    unicode_files.append(TextUNICODE(file_path, frecvente))

                is_bmp_file, width, height, bpp = is_bmp(content)
                if is_bmp_file:
                    bmp_files.append(BMP(file_path, frecvente, width, height, bpp))

    return xml_ascii_files, unicode_files, bmp_files


if __name__ == "__main__":
    import sys

    if len(sys.argv) != 2:
        print("Utilizare: python script.py <director>")
        sys.exit(1)

    target_dir = sys.argv[1]
    xml_files, unicode_files, bmp_files = scan_directory(target_dir)

    print("Fișiere XML ASCII:")
    for xml in xml_files:
        print(f"- {xml.get_path()}")
        print(f"  Primul tag: {xml.get_first_tag()}")

    print("\nFișiere UNICODE:")
    for uni in unicode_files:
        print(f"- {uni.get_path()}")

    print("\nFișiere BMP:")
    for bmp in bmp_files:
        bmp.show_info()