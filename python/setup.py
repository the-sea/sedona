from setuptools import setup, find_packages
from os import path
from geo_pyspark import version

here = path.abspath(path.dirname(__file__))
jars_relative_path = "geo_pyspark/jars"


setup(
    name='geo_pyspark',
    version=version,
    description='GeoSpark Python Wrapper',
    url='https://github.com/Imbruced/geo_pyspark',
    author='Pawel Kocinski',
    author_email='pawel93kocinski@gmail.com',
    packages=find_packages(exclude=['tests']),
    python_requires='>=3.6',
    install_requires=['pyspark', 'findspark', 'pandas', 'geopandas', 'attrs'],
    project_urls={
        'Bug Reports': 'https://github.com/Imbruced/geo_pyspark'
    },
    package_data={
        'geo_pyspark.jars.2_3': ["*.jar"],
        'geo_pyspark.jars.2_4': ["*.jar"],
        'geo_pyspark.jars.2_2': ["*.jar"]
    }
)
